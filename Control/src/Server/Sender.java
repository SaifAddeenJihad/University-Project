package Server;

import Server.Main;

import java.net.*;

public class Sender implements Runnable {

    @Override
    public void run() {
        try{

            MulticastSocket multicastSocket = new MulticastSocket();
            InetAddress multicastGroup = InetAddress.getByName("239.0.0.1"); // Multicast IP address
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress ip = InetAddress.getByName("192.168.1.102");
            while(true){
                if(Main.baos.isEmpty())
                    continue;
                byte[] compressed= Main.baos.remove();
                int chunkSize = 65507;
                int totalChunks = (int) Math.ceil((double) compressed.length / chunkSize);

                for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
                    int offset = chunkIndex * chunkSize;
                    int length = Math.min(chunkSize, compressed.length - offset);
                    byte[] chunkBuffer = new byte[length];
                    System.arraycopy(compressed, offset, chunkBuffer, 0, length);

                    DatagramPacket datagramPacket = new DatagramPacket(chunkBuffer, chunkBuffer.length, ip, 1234);
                   datagramSocket.send(datagramPacket);
                }
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
