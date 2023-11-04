package Stream;

import network.ConnectionFactory;
import network.IConnectionNames;
import network.MulticastReceiver;
import org.xerial.snappy.Snappy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MulticastImageReceiver {
    private static JFrame frame;
    private static JLabel imageLabel;

    public static void start() {

        MulticastReceiver connection = (MulticastReceiver) ConnectionFactory.getIConnection(IConnectionNames.MULTICAST_RECEIVER);
        connection.initialize(50001, "239.0.0.1");//ip from config file

        initializeFrame();

        while (true) {
            displayImage(connection);
        }
    }

    private static void initializeFrame() {
        frame = new JFrame("Multicast Image Receiver");
        imageLabel = new JLabel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(imageLabel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void displayImage(MulticastReceiver connection) {
        try {
            byte[] data = connection.receive();
            byte[] uncompressedBuffer = Snappy.uncompress(data);
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
