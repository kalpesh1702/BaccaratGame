
import java.net.InetAddress;


public class ClientInfo {
    public int port;
    public String name;
    public InetAddress address;

    public ClientInfo(String name, InetAddress address, int port){
        this.port=port;
        this.name=name;
        this.address=address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }


}
