package Services;

import network.ConnectionFactory;
import network.IConnection;
import network.IConnectionNames;

import java.net.InetAddress;
import java.util.List;

public class CommandService {
    private IConnection connection;
    private final int port = 50000;

    public void sendCommand(List<InetAddress> addressList, Commands command){
        for(InetAddress address: addressList){
            sendCommand(address, command.label);
        }
    }

    private void sendCommand(InetAddress address, String command){
        connection = ConnectionFactory.getIConnection(IConnectionNames.TCP_CLIENT);
        connection.initialize(port, "127.0.0.1");
        connection.send(command.getBytes());
        connection.close();
    }

    public enum Commands {
        STREAM("Stream"),
        CONTROL("Control"),
        FILE_TRANSFER("File Transfer"),
        FILE_COLLECT("File Collect"),
        SHUTDOWN("Shutdown"),
        FREEZE("Freeze"),
        UNFREEZE("Unfreeze"),
        OPEN_APP("Open App"),
        BLOCK_INTERNET("Block Internet");

        public final String label;
        private Commands(String label){
            this.label = label;
        }
    }
}
