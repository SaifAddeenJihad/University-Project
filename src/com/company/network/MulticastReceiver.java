package com.company.network;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class MulticastReceiver implements IConnection {

    private final int MAX_BUFFER_SIZE = 65507;
    private int port;
    private InetAddress group=null;
    private MulticastSocket multicastSocket=null;
    private NetworkInterface networkInterface = null;

    public void initialize(int port, String ipAddress) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isUp() && !iface.isLoopback() && !iface.isVirtual()) {
                    networkInterface = iface;
                    break;
                }

            }

            if (networkInterface != null) {
                this.port=port;
                multicastSocket = new MulticastSocket(port);
                group = InetAddress.getByName(ipAddress);
                multicastSocket.setNetworkInterface(networkInterface);
                multicastSocket.joinGroup(new InetSocketAddress(group, port), networkInterface);

            } else {
                System.out.println("No suitable network interface found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void send(byte[] data) {

    }

    @Override
    public byte[] receive() {

        byte[] receiveData = new byte[MAX_BUFFER_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        try {
            multicastSocket.receive(receivePacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return receivePacket.getData();

    }


    public void close() {
        try {
            multicastSocket.leaveGroup(new InetSocketAddress(group, port), networkInterface);
            multicastSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
