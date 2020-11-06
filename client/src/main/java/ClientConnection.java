import java.io.IOException;
import java.net.*;
import java.util.Map;

public class ClientConnection {

    InetAddress ip;
    DatagramSocket socket;
    int port;
    Thread send;


    ClientConnection(String ipAddress, int portNumber) throws Exception {
        port = portNumber;
        ip = InetAddress.getByName(ipAddress);
        createConnection();
    }

    public void createConnection() throws SocketException {
        socket = new DatagramSocket();
        socket.connect(ip, port);
    }

    public void send(Map<String,Object> message) {

        send = new Thread("Send Thread") {
            public void run() {
                try {
                    final byte[] data = Helper.convertObjectToByteArray(message);
                    DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
                    socket.send(packet);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        send.start();
    }

    public Map<String, Object> receive() throws Exception {
        socket.setSoTimeout(2000);
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
        } catch (IOException ex) {
            return null;
        }
        return Helper.convertByteArrayToObject(packet.getData());
    }

}
