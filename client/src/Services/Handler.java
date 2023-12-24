package Services;

import FileTransfer.FileReceiver;
import FileTransfer.FileSender;
import MiniServices.MiniServices;
import RemoteControl.Server.Capture;
import RemoteControl.Server.ReceiveEvents;
import RemoteControl.Server.Sender;
import Stream.MulticastImageReceiver;
import auxiliaryClasses.IPorts;
import network.ConnectionFactory;
import network.IConnectionNames;
import network.TCPServer;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.ResourceBundle;

public class Handler {
    public static volatile Queue<byte[]> baos =new LinkedList<>();

    private Handler (){}
    public static void startStream(boolean multicast) {
        MulticastImageReceiver.setIsMulticast(multicast);
        MulticastImageReceiver.setStreamIsRunning(true);
        MulticastImageReceiver.initialize("239.0.0.1");
        MulticastImageReceiver.start();

//        MiniServices.enableKeyboard();
    }

    public static void closeStream() {
        MulticastImageReceiver.setStreamIsRunning(false);
        MulticastImageReceiver.close();
//        MiniServices.enableKeyboard();
    }
    public static void startControl() throws Exception {
        MiniServices.disableKeyboard();
        Thread t1=new Thread(new Capture());
        t1.start();
        Thread t2=new Thread(new Sender());
        t2.start();
        ServerSocket socket = null;
        System.out.println("sending images");
        Socket sc = null;
        Robot robot = null;
        try {
            TCPServer tcpServer = (TCPServer) ConnectionFactory.getIConnection(IConnectionNames.TCP_SERVER);
            tcpServer.initialize(IPorts.CONTROL+10,null);
            //socket = new ServerSocket(1235);
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
            robot = new Robot(gDev);
            //sc = socket.accept();
            Thread t3=new Thread(new ReceiveEvents(tcpServer.getInputStream(), robot));
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public static void stopControl() throws Exception {
        MiniServices.enableKeyboard();
        Capture.isRunning(false);
        Sender.isRunning(false);
        ReceiveEvents.isRunning(false);
    }
    public static void receiveFile() {
        Properties appProps = new Properties();
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            appProps.load(new FileInputStream(rootPath + "Config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Config.properties file is missing!");
        }
        Thread fileReceiver = new Thread(new FileReceiver(appProps.getProperty("server-ip")));
        fileReceiver.start();
    }
    public static void sendFile() {
        Properties appProps = new Properties();
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            appProps.load(new FileInputStream(rootPath + "Config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Config.properties file is missing!");
        }
        Thread fileSender = new Thread(new FileSender(appProps.getProperty("server-ip")));
        fileSender.start();
    }



}
