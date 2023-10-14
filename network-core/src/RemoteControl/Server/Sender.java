package RemoteControl.Server;

import network.ConnectionFactory;
import network.IConnection;
import network.IConnectionNames;
import network.UDPClient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Sender implements Runnable {

    @Override
    public void run() {
        try{

            //MulticastSocket multicastSocket = new MulticastSocket();
            //InetAddress multicastGroup = InetAddress.getByName("239.0.0.1"); // Multicast IP address
            UDPClient udpClient= (UDPClient) ConnectionFactory.getIConnection(IConnectionNames.UDP_CLIENT);
            udpClient.initialize(1234,"192.168.1.82");
            //DatagramSocket datagramSocket = new DatagramSocket();
            //InetAddress ip = InetAddress.getByName("127.0.0.1");
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

                    //DatagramPacket datagramPacket = new DatagramPacket(chunkBuffer, chunkBuffer.length, ip, 1234);
                    udpClient.send(chunkBuffer);
                }
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
