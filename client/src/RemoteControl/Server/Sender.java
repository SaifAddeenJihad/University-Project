package RemoteControl.Server;

import network.ConnectionFactory;
import network.IConnectionNames;
import network.UDPClient;

public class Sender implements Runnable {
    private final int chunkSize= 65507;

    @Override
    public void run() {
        try{

            UDPClient udpClient= (UDPClient) ConnectionFactory.getIConnection(IConnectionNames.UDP_CLIENT);
            udpClient.initialize(50002,"192.168.1.82");//server ip from config file

            while(true){
                if(Main.baos.isEmpty())
                    continue;
                sendImage(udpClient);
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    private void sendImage(UDPClient udpClient){

        byte[] compressed= Main.baos.remove();
        int totalChunks = (int) Math.ceil((double) compressed.length / chunkSize);

        for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
            int offset = chunkIndex * chunkSize;
            int length = Math.min(chunkSize, compressed.length - offset);
            byte[] chunkBuffer = new byte[length];
            System.arraycopy(compressed, offset, chunkBuffer, 0, length);

            udpClient.send(chunkBuffer);
        }
    }
}
