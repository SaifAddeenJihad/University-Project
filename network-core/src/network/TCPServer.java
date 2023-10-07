package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends TCP {

    private ServerSocket serverSocket = null;




    @Override
    public void initialize(int port, String ipAddress) {
        try {
            serverSocket = new ServerSocket(port);

            this.socket =serverSocket.accept();

            this.input = socket.getInputStream();
            this.output = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
