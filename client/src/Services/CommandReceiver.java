package Services;

import MiniServices.MiniServices;
import MiniServices.Screen;
import auxiliaryClasses.IPorts;
import network.ConnectionFactory;
import network.IConnection;
import network.IConnectionNames;
import network.TCPServer;
import MiniServices.BlockWebsites;

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
            } catch (Exception e) { // TODO: Change this
                e.printStackTrace();
            }
            finally {
                connection.close();
            }
        }
    }
    private void execute(String command) throws Exception {
        String commandName = command;
        String commandParam = null;

        if(command.contains("Website")){
            commandName = command.substring(0, command.indexOf(':'));
            commandParam = command.substring(command.indexOf(':') + 1);
        }

        switch (commandName){
            case "Stream":
                Handler.startStream(true);
                break;
            case "Close Stream":
                Handler.closeStream();
                break;
            case "UDP Stream":
                Handler.startStream(false);
            case "Close UDP Stream":
                Handler.closeStream();
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
            case "Allow Default":
                BlockWebsites.allowDefault();
                break;
            case "Unblock All":
                BlockWebsites.unblockAll();
                break;
            case "Allow Website":
                BlockWebsites.allowCustom(commandParam);
                break;
            case "Block Website":
                BlockWebsites.blockCustom(commandParam);
                break;
            case "Open Website":
                MiniServices.openApp(commandParam);
                break;
            default:
                System.out.println("Command not found");
        }
    }
}
