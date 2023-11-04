package Services;

import Stream.Capture;
import Stream.Sender;
import auxiliaryClasses.Client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Handler {
    private Handler(){}
    public static volatile Queue<byte[]> baos =new LinkedList<>();
    public static void startStream(Map<Integer, Client> data) throws UnknownHostException {

        ArrayList<InetAddress> ipAdresses = getIneAdresses((ArrayList<Client>) data.values());
        CommandService.sendCommand(ipAdresses, Commands.STREAM);
        Thread t1=new Thread(new Capture());
        t1.start();
        Thread t2=new Thread(new Sender());
        t2.start();
    }
    public static void closeStream(Map<Integer, Client> data) throws UnknownHostException {
        ArrayList<InetAddress> ipAdresses = getIneAdresses((ArrayList<Client>) data.values());
        CommandService.sendCommand(ipAdresses, Commands.CLOSE_STREAM);
    }

    private static ArrayList<InetAddress> getIneAdresses(ArrayList<Client> clients) throws UnknownHostException {
        ArrayList<InetAddress> ipAdresses =new ArrayList<>();
        for (Client client: clients)
            ipAdresses.add(InetAddress.getByName(client.getIpAddress()));
        return ipAdresses;
    }
}
