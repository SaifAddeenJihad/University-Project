package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

class MulticastSender implements IConnection {

    private int port;
    private InetAddress group = null;
    private MulticastSocket multicastSocket = null;


    public void initialize(int port, String ipAddress) {

        try {
            group = InetAddress.getByName(ipAddress);
            this.multicastSocket = new MulticastSocket();
            this.port = port;

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
    public void sendString(String message) {

    }

    @Override
    public byte[] receive() {
        return null;
    }

    @Override
    public String receiveString() {
        return null;
    }


    public void close() {
        multicastSocket.close();
    }
}
