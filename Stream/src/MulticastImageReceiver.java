import org.xerial.snappy.Snappy;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class MulticastImageReceiver {
    public static void main(String[] args) throws IOException {
        InetAddress multicastGroup = InetAddress.getByName("239.0.0.1"); // Multicast IP address
        int port = 1234;

        MulticastSocket multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(multicastGroup);

        int maxBufferSize = 65507;

        JFrame frame = new JFrame("Multicast Image Receiver");
        JLabel imageLabel = new JLabel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(imageLabel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while (true) {
            byte[] fullBuffer = new byte[0];

            while (true) {
                byte[] chunk = new byte[maxBufferSize];
                DatagramPacket datagramPacket = new DatagramPacket(chunk, maxBufferSize);
                multicastSocket.receive(datagramPacket);

                byte[] receivedData = datagramPacket.getData();
                int receivedLength = datagramPacket.getLength();

                // Concatenate received chunk to the full buffer
                byte[] newBuffer = new byte[fullBuffer.length + receivedLength];
                System.arraycopy(fullBuffer, 0, newBuffer, 0, fullBuffer.length);
                System.arraycopy(receivedData, 0, newBuffer, fullBuffer.length, receivedLength);
                fullBuffer = newBuffer;

                if (receivedLength < maxBufferSize) {
                    break;
                }
            }

            try {
                //InputStream inStream = new ByteArrayInputStream(fullBuffer);
                byte[] uncompressedBuffer = Snappy.uncompress(fullBuffer);
                InputStream inStream = new ByteArrayInputStream(uncompressedBuffer);
                BufferedImage image = ImageIO.read(inStream);

                SwingUtilities.invokeLater(() -> {
                    imageLabel.setIcon(new ImageIcon(image));
                    frame.pack();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
