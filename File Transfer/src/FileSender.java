import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class FileSender implements Runnable {
    private String clientSavePath;
    private String senderFilePath;

    public FileSender(String clientSavePath, String senderFilePath) {
        this.clientSavePath = clientSavePath;
        this.senderFilePath = senderFilePath;
    }

    @Override
    public void run() {
        ObjectOutputStream outputStream;
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            Socket socket = serverSocket.accept();
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(clientSavePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File file = new File(senderFilePath);
        try {
            if (file.isDirectory()) {
                outputStream.writeBoolean(true);
                outputStream.flush();
                sendDirectory(file, outputStream);
            } else {
                outputStream.writeBoolean(false);
                outputStream.flush();
                sendFile(file, outputStream);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void sendFile(File file, ObjectOutputStream outputStream) throws IOException {

        outputStream.writeUTF(file.getName());
        outputStream.flush();
        int fileSize = (int) file.length();
        outputStream.writeInt(fileSize);
        outputStream.flush();
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            outputStream.flush();
        }

        fileInputStream.close();
    }

    private void sendDirectory(File directory, ObjectOutputStream outputStream) throws IOException {
        outputStream.writeUTF(directory.getName());
        outputStream.flush();
        File[] files = directory.listFiles();
        // Send the number of files in the directory
        outputStream.writeInt(files != null ? files.length : 0);
        outputStream.flush();
        if (files != null) {
            // Send each file in the directory
            for (File file : files) {
                if (file.isDirectory()) {
                    sendDirectory(file, outputStream);
                } else {
                    sendFile(file, outputStream);
                }
            }
        }

    }
}
