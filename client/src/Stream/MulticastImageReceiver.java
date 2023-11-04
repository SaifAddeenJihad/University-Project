package Stream;

import network.ConnectionFactory;
import network.IConnectionNames;
import network.MulticastReceiver;
import org.xerial.snappy.Snappy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import net.coobird.thumbnailator.Thumbnails;

public class MulticastImageReceiver {
    private static int port;
    private static String ipAddress;
    private static JFrame frame;
    private static JLabel imageLabel;
    private static boolean streamIsRunning=false;
    public static void setStreamIsRunning(boolean isRunning){
        streamIsRunning=isRunning;
    }
    public static void initialize(int intPort, String intIpAddress){
        port=intPort;
        ipAddress=intIpAddress;
    }
    public static void start() throws IOException {
        InetAddress multicastGroup = InetAddress.getByName("239.0.0.1"); // Multicast IP address
        int port = 50001;

        MulticastSocket multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(multicastGroup);

        int maxBufferSize = 65507;

        JLabel imageLabel = new JLabel();
        JFrame frame = new JFrame("Multicast Image Receiver");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true); // Make the JFrame undecorated
        frame.getContentPane().add(imageLabel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
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
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                image = Thumbnails.of(image).forceSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight()).asBufferedImage();
                BufferedImage finalImage = image;
                SwingUtilities.invokeLater(() -> {
                    imageLabel.setIcon(new ImageIcon(finalImage));
                    frame.pack();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }

/*        MulticastReceiver connection = (MulticastReceiver) ConnectionFactory.getIConnection(IConnectionNames.MULTICAST_RECEIVER);
        connection.initialize(50001, "239.0.0.1");//ip from config file

        initializeFrame();

        while (streamIsRunning) {
            displayImage(connection);
        }
        frame.dispose();
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
    }*/

