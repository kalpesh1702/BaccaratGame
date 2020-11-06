import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.IOException;
import java.net.*;
import java.net.UnknownHostException;
import java.util.*;

public class ServerConnection implements Runnable {

    private int port;
    public long serverStartTime;
    public List<ClientInfo> clients = new ArrayList<ClientInfo>();

    DatagramSocket socket;
    InetAddress ip;
    Thread run, send, receive, manage;
    Boolean running = false, raw = false, exits = false;

    public ServerConnection(int port) {

        this.port = port;

        try {
            ip = InetAddress.getByName("localhost");
            ServerMain.setMessagesOnServerinfo("Server is Running on localhost and port number = "+ port);
            socket = new DatagramSocket(port);
        } catch (SocketException  | UnknownHostException ex) {
            ex.printStackTrace();
        }

        run = new Thread((Runnable) this, "Run Thread");
        run.start();
    }

    public int getPort() {
        return port;
    }

    public void manageClients() {
        manage = new Thread("Manage") {
            public void run() {
                System.out.println("Manage Thead is running");
            }
        };
        manage.start();
    }

    public void send(Map<String,Object> message, ClientInfo client) {
        InetAddress address = client.getAddress();
        int port = client.getPort();
        Thread send = new Thread("Server Send Thread") {
            public void run() {
                try {
                    final byte[] data = Helper.convertObjectToByteArray(message);
                    DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                    socket.send(packet);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        send.start();
    }

    public void receive() {
        receive = new Thread(" Server Receive Thread") {
            @Override
            public void run() {
                while (running) {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    process(packet);
                }
            }
        };
        receive.start();
    }

    public void disconnect(String name, boolean status) {
        ClientInfo c = null;
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getName() == name) {
                c = clients.get(i);
                clients.remove(i);
                break;
            }
        }
    }

    public void quit() {
        for (int i = 0; i < clients.size(); i++) {
            disconnect(clients.get(i).getName(), true);
        }
        running = false;
        try {
            socket.close();
            ServerMain.setMessagesOnServerinfo("Server Down ! ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendToAll(Map<String, Object> message) {
        
        for (int i = 0; i < clients.size(); i++) {
            ClientInfo client = clients.get(i);
            send(message, client);
        }
    }

    public void run() {
        running = true;
        serverStartTime = System.currentTimeMillis();
        ServerMain.setMessagesOnServerinfo("Server Started Time : " + serverStartTime);
        ServerMain.setMessagesOnServerinfo("Server Started on Port " + port);
        manageClients();
        receive();
    }

    public void process(DatagramPacket packet){

        try {
            Map<String, Object> message = new HashMap<String, Object>();
            message = Helper.convertByteArrayToObject(packet.getData());
            ServerMain.setMessagesOnServerinfo("received message from Clients");

            String type = (String) message.get("type");
            String status,content;

            if (type.equals("connect")) {
                String clientName = (String) message.get("name");
                int clientPortNumber = packet.getPort();
                InetAddress clientIp = packet.getAddress();
                ServerMain.setMessagesOnServerinfo(clientName + " is trying to connect.");

                Iterator iterator = clients.iterator();
                while(iterator.hasNext()) {
                    ClientInfo client = (ClientInfo) iterator.next();
                    if (client != null && client.getName().equals(clientName)){
                        status = "Failed";
                        content = clientName + " has already connected.";
                        send(Helper.prepareConnectionMessageToClients(status,content,type), client);
                        ServerMain.setMessagesOnServerinfo(content);
                        return;
                    }
                }
                status = "Success";
                content = clientName + " is Successfully connected.";
                ClientInfo newClient = new ClientInfo(clientName, clientIp, clientPortNumber);
                clients.add(newClient);
                send(Helper.prepareConnectionMessageToClients(status,content,type), newClient);
                ServerMain.setMessagesOnServerinfo(content);
                return;
            }

            if(type.equals("disconnect")){
                String clientName = (String) message.get("name");
                
                ClientInfo requestedClient = null;

                Iterator iterator = clients.iterator();
                while(iterator.hasNext()) {
                    ClientInfo client = (ClientInfo) iterator.next();
                    if (client.getName().equals(clientName)){
                        requestedClient = client;
                        break;
                    }
                }

                if(requestedClient == null){
                    status = "Failed";
                    content = "Something went wrong.Please try later.";
                    ServerMain.setMessagesOnServerinfo(content);
                    send(Helper.prepareConnectionMessageToClients(status,content,type), requestedClient);
                    return;
                }

                status = "Success";
                content = clientName + " is disConnected Successfully on server";
                send(Helper.prepareConnectionMessageToClients(status,content,type), requestedClient);
                clients.remove(requestedClient);
                ServerMain.setMessagesOnServerinfo(content);
                return;
            }

            if(type.equals("Play") || type.equals("Play Again")){
                String clientName = (String) message.get("name");
                String bettingOn = (String) message.get("bettingOn");
                int amount = (Integer) message.get("amount");
                ServerMain.setMessagesOnServerinfo(clientName + " wants to " + type);
                ServerMain.setMessagesOnServerinfo(clientName + " bet is $" + amount + " on " + bettingOn);

                BaccaratGame game = new BaccaratGame(bettingOn, amount);
                game.dealer.shuffleDeck();
                game.playerHand = game.dealer.dealHand();
                game.bankerHand = game.dealer.dealHand();

                if(game.gameLogic.handTotal(game.playerHand) >= 8 || game.gameLogic.handTotal(game.bankerHand) >= 8){

                }
                else if(game.gameLogic.evaluatePlayerDraw(game.playerHand)){
                    game.playerHand.add(game.dealer.drawOne());
                    if(game.gameLogic.evaluateBankerDraw(game.bankerHand, game.playerHand.get(2))){
                        game.bankerHand.add(game.dealer.drawOne());
                    }
                }
                else if(game.gameLogic.evaluateBankerDraw(game.bankerHand, null)){
                    game.bankerHand.add(game.dealer.drawOne());
                }

                double winningAmount = game.evaluateWinnings();

                if(winningAmount >= 0){
                    ServerMain.setMessagesOnServerinfo(clientName + " win the game. winning amount = $" + winningAmount);
                }
                else{
                    ServerMain.setMessagesOnServerinfo(clientName + " lose the game. losing amount = $" + Math.abs(winningAmount));
                }
                Map<String, Object> gameInfo = new HashMap<String, Object>();
                gameInfo.put("type", "result");
                gameInfo.put("playerHand", game.playerHand);
                gameInfo.put("bankerHand", game.bankerHand);
                gameInfo.put("winningAmount", winningAmount);
                gameInfo.put("playerHandTotal", game.gameLogic.handTotal(game.playerHand));
                gameInfo.put("bankerHandTotal", game.gameLogic.handTotal(game.bankerHand));
                gameInfo.put("winner", game.gameLogic.whoWon(game.playerHand, game.bankerHand));
                gameInfo.put("bettingOn", bettingOn);
                gameInfo.put("amount", amount);
                gameInfo.put("status", "Success");
                gameInfo.put("content", "played successfully");

                Iterator iterator = clients.iterator();
                while(iterator.hasNext()) {
                    ClientInfo client = (ClientInfo) iterator.next();
                    if (client != null && client.getName().equals(clientName)){
                        send(gameInfo, client);
                    }
                }
            }

        }
        catch (Exception e){
            ServerMain.setMessagesOnServerinfo(e.getMessage());
        }
    }

}
