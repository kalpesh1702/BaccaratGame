
import java.net.InetAddress;


public class ServerInfo {
    public int port;
    public InetAddress address;

    public ServerInfo(InetAddress address, int port){
        this.port=port;
        this.address=address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

}
