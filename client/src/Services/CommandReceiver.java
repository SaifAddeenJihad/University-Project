package Services;

import network.ConnectionFactory;
import network.IConnection;
import network.IConnectionNames;

public class CommandReceiver {
    private IConnection connection;
    private final int port = 50000;

    public CommandReceiver(){
        connection = ConnectionFactory.getIConnection(IConnectionNames.TCP_SERVER);
    }

    public void start() {
        while(true){
            try{
                connection.initialize(port, null);
                String command = new String (connection.receive());
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
