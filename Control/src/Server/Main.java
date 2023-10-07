package Server;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static volatile Queue<byte[]> baos =new LinkedList<>();
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new Capture());
        t1.start();
        Thread t2=new Thread(new Sender());
        t2.start();
        ServerSocket socket = null;

        /*Socket sc = null;
        Robot robot = null;
        try {
            socket = new ServerSocket(1235);
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
            robot = new Robot(gDev);
            sc = socket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Thread t3=new Thread(new ReceiveEvents(sc, robot));*/
    }
}