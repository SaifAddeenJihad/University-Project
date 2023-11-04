package Services;

import MiniServices.MiniServices;
import Stream.MulticastImageReceiver;

public class Handler {
    private Handler (){}
    public static void startStream() throws Exception {
        try {
            MiniServices.disableMouse();
            MiniServices.disableKeyboard();
            MulticastImageReceiver.setStreamIsRunning(true);
            MulticastImageReceiver.start();
        }
        catch (Exception e){}

        finally {
            MiniServices.enableMouse();
            MiniServices.enableKeyboard();
        }
    }
    public static void CloseStream(){
        MulticastImageReceiver.setStreamIsRunning(false);
    }
}
