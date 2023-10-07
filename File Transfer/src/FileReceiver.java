import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReceiver {
    InetAddress serverIp;
    private String clientSavePath;

    public FileReceiver(InetAddress serverIp) {
        this.serverIp = serverIp;
    }

    public void start() throws IOException, ClassNotFoundException, URISyntaxException {
        Socket socket = new Socket(serverIp, 7777);
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        clientSavePath = (String) inputStream.readObject();
        Boolean isDirectory = inputStream.readBoolean();
        if (isDirectory) {
            receiveDirectory(inputStream);
        } else {
            receiveFile(inputStream);
        }
    }

    private void receiveFile(ObjectInputStream inputStream) throws IOException {
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

    private void receiveDirectory(ObjectInputStream inputStream) throws IOException, URISyntaxException {
        String directoryName = inputStream.readUTF();
        int numberOfFiles = inputStream.readInt();
        clientSavePath=clientSavePath+"\\"+directoryName;
        for(int i=0;i<numberOfFiles;i++)
            receiveFile(inputStream);
    }

}
