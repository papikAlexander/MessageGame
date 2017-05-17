package game.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by alex on 16.05.17.
 */
public class Player {
    private boolean ready;
    private int port = 8888;
    private InetAddress ip = InetAddress.getLocalHost();

    public Player(boolean ready) throws UnknownHostException {
        this.ready = ready;
    }


    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }
}
