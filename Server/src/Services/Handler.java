package Services;

import Stream.Capture;
import Stream.Sender;
import auxiliaryClasses.Client;
import javafx.collections.ObservableList;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class Handler {
    private Handler(){}
    public static volatile Queue<byte[]> baos =new LinkedList<>();
    public static void startStream(List<String> IPs) throws UnknownHostException {

        ArrayList<InetAddress> ipAdresses = getIneAdresses((ObservableList<String>) IPs);
        CommandService.sendCommand(ipAdresses, Commands.STREAM);
        Thread t1=new Thread(new Capture());
        t1.start();
        Thread t2=new Thread(new Sender());
        t2.start();
    }
    public static void closeStream(List<String> IPs) throws UnknownHostException {
        ArrayList<InetAddress> ipAdresses = getIneAdresses((ObservableList<String>) IPs);
        CommandService.sendCommand(ipAdresses, Commands.CLOSE_STREAM);
    }

    /*private static ArrayList<InetAddress> getIneAdresses(ArrayList<Client> clients) throws UnknownHostException {
        ArrayList<InetAddress> ipAdresses =new ArrayList<>();
        for (Client client: clients)
            ipAdresses.add(InetAddress.getByName(client.getIpAddress()));
        return ipAdresses;
    }*/
    private static ArrayList<InetAddress> getIneAdresses(ObservableList<String> IPs) throws UnknownHostException {
        ArrayList<InetAddress> ipAdresses =new ArrayList<>();
        for (String IP: IPs)
            ipAdresses.add(InetAddress.getByName(IP));
        return ipAdresses;
    }
}
