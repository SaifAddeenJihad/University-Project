import network.*;

import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;

public class Main {
    public static void main(String[] args) {

//        UDPServer udpServer=new UDPServer();
//
//
//        udpServer.initialize(4000);
//        String str= udpServer.receive();
//        System.out.println(str);
//        udpServer.send(str);
//        udpServer.close();
//
//        System.out.println("done");

//        MulticastSender multicastSender=new MulticastSender();
//        multicastSender.initialize(4000,"239.0.0.1");
//        try {
//            Thread.sleep(15_000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        multicastSender.send("hey this is just test");
//        multicastSender.close();


        IConnection tcpServer=ConnectionFactory.getIConnection(IConnectionNames.TCP_SERVER);
        tcpServer.initialize(4000,null);
        System.out.println(tcpServer.receiveString());
        tcpServer.sendString("that is true heeyyy client");






    }
}