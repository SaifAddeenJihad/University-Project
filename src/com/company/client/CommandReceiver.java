package com.company.client;

import com.company.network.ConnectionFactory;
import com.company.network.IConnection;
import com.company.network.IConnectionNames;

public class CommandReceiver{
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
    private void execute(String command){
        switch (command){
            case "Stream":
                System.out.println("Streaming started.");
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
