
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static volatile Queue<byte[]> baos =new LinkedList<>();
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new Capture());
        t1.start();
        Thread t2=new Thread(new Sender());
        t2.start();

    }
}