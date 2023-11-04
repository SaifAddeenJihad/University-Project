package Services;

import MiniServices.MiniServices;
import Stream.MulticastImageReceiver;

public class Handler {
    private Handler (){}
    public static void startStream() throws Exception {
        try {
            MiniServices.disableKeyboard();
            MulticastImageReceiver.setStreamIsRunning(true);
            MulticastImageReceiver.initialize(50001,"239.0.0.1");
            MulticastImageReceiver.start();
        }
        catch (Exception e){}

        finally {

            MiniServices.enableKeyboard();
        }
    }
    public static void CloseStream() throws Exception {
        MulticastImageReceiver.setStreamIsRunning(false);
        MiniServices.enableKeyboard();
    }
    public static void startControl(){}
    public static void stopControl(){

    }

}
