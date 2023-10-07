import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException {
        System.out.println("Hello world!");
        //sender
        String savePath="D:\\shared";
        String sendPath="D:Adobe Illustrator CC 2020 v24.1.2.402 (x64) Patched";
        Runnable fileSender =new FileSender(savePath,sendPath);
        new Thread(fileSender).start();
        //receiver
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        FileReceiver fileReceiver=new FileReceiver(ip);
        fileReceiver.start();

    }

}