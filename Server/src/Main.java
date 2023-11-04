import Services.Handler;
import auxiliaryClasses.Lan;
import auxiliaryClasses.LanCreator;
import network.*;

import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;

public class Main {
    public static void main(String[] args) {


        Lan lan= LanCreator.createLan("192.168.1.2","192.168.1.3","239.0.0.1",1);
        try {

            Handler.startStream(lan.getClients());

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }


    }
}