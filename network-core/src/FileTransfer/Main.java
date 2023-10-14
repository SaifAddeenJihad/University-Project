package FileTransfer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException {
        System.out.println("Hello world!");
        //sender
        String savePath="D:\\shared";
        String sendPath="C:\\Users\\Saif\\Downloads\\network-core\\network-core.7z";
        Runnable fileSender =new FileSender(savePath,sendPath);
        new Thread(fileSender).start();
        //receiver
        //InetAddress ip = InetAddress.getByName("127.0.0.1");
        //FileReceiver fileReceiver=new FileReceiver(ip);
        //fileReceiver.start();

    }

}