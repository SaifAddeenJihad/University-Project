package Services;

import MiniServices.MiniServices;
import MiniServices.Screen;
import auxiliaryClasses.IPorts;
import network.ConnectionFactory;
import network.IConnection;
import network.IConnectionNames;
import network.TCPServer;

public class CommandReceiver {
    private TCPServer connection;
    private final int port = IPorts.TOKENS;

    public CommandReceiver(){
        connection = (TCPServer) ConnectionFactory.getIConnection(IConnectionNames.TCP_SERVER);
    }

    public void start() {
        //connection.initialize(port, null);
        while(true){
            try{
                connection.initialize(port, null);
                String command = connection.receiveString();
                System.out.println(command);
                execute(command);
            } catch (Exception e) {

            }
            finally {
                connection.close();
            }
        }
    }
    private void execute(String command) throws Exception {
        switch (command){
            case "Stream":
                Handler.startStream();
                break;
            case "Close Stream":
                Handler.CloseStream();
                break;
            case "Control":
                Handler.startControl();
                break;
            case "Stop Control":
                Handler.stopControl();
                break;
            case "File Transfer":
                Handler.receiveFile();
                break;
            case "File Collect":
                Handler.sendFile();
                break;
            case "Shutdown":
                MiniServices.shutDown();
                break;
            case "Freeze":
                Screen.turnOnScreen();
                MiniServices.disableKeyboard();
                MiniServices.disableMouse();
                break;
            case "Unfreeze":
                Screen.turnOffScreen();
                MiniServices.enableKeyboard();
                MiniServices.enableMouse();
                break;
            case "Block Internet":
                break;
            default:
                MiniServices.openApp(command);

        }
    }
}
