package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public abstract class TCP implements IConnection {

    protected Socket socket=null;
    protected InputStream input = null;
    protected DataOutputStream output=null;

    public void send(byte[] data) {
        try {
            output.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public byte[] receive(){
        byte[] serverResponse = null;
        try {
            serverResponse = input.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return serverResponse;
    }
}
