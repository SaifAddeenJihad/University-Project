package FileTransfer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import network.ConnectionFactory;
import network.IConnectionNames;
import network.TCPClient;
public class FileReceiver {
    InetAddress serverIp;
    private String clientSavePath;
    private DataInputStream inputStream;
    public FileReceiver(InetAddress serverIp) {
        this.serverIp = serverIp;
    }

    public void start() throws IOException, ClassNotFoundException, URISyntaxException {
        TCPClient connection= (TCPClient) ConnectionFactory.getIConnection(IConnectionNames.TCP_CLIENT);

        //Socket socket = new Socket(serverIp, 7777);
        inputStream = connection.getInputStream();
        clientSavePath = inputStream.readUTF();
        Boolean isDirectory = inputStream.readBoolean();
        if (isDirectory) {
            receiveDirectory();
        } else {
            receiveFile();
        }
    }

    private void receiveFile() throws IOException {
        String fileName = inputStream.readUTF();

        // Receive the file content
        int fileSize = inputStream.readInt();
        long iterations = fileSize / 1024;
        byte[] fileContent = new byte[fileSize];
        inputStream.readFully(fileContent);

        // Save the file to the local filesystem
        Path filePath = Path.of(clientSavePath + "\\" + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, fileContent);

        System.out.println("File received: " + filePath.toString());
    }

    private void receiveDirectory() throws IOException, URISyntaxException {
        String directoryName = inputStream.readUTF();
        int numberOfFiles = inputStream.readInt();
        clientSavePath=clientSavePath+"\\"+directoryName;
        for(int i=0;i<numberOfFiles;i++)
            receiveFile();
    }

}
