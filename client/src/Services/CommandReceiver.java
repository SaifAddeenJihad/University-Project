package Services;

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
        while(true){
            try{

                connection.initialize(port, null);
                System.out.println("listning");
                String command = connection.receiveString();
                System.out.println(command);
                execute(command);
            } catch (Exception e) {

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
                break;
            case "File Transfer":
                break;
            case "File Collect":
                break;
            case "Shutdown":
                break;
            case "Freeze":
                break;
            case "Unfreeze":
                break;
            case "Open App":
                break;
            case "Block Internet":
                break;

        }
    }
}
