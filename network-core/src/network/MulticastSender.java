package network;

import java.io.IOException;
import java.net.*;

public class MulticastSender implements IConnection{

    private int port;
    private InetAddress group=null;
    private MulticastSocket multicastSocket=null;


    public void initialize(int port, String ipAddress) {

        try {
            group = InetAddress.getByName(ipAddress);
            this.multicastSocket = new MulticastSocket();
            this.port=port;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void send(byte[] data) {


        DatagramPacket sendPacket = new DatagramPacket(data, data.length, group, port);
        try {
            multicastSocket.send(sendPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] receive() {
        return null;
    }


    public void close() {
        multicastSocket.close();
    }
}
