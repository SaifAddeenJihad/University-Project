package Client;

import Server.Sender;
import org.xerial.snappy.Snappy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class MulticastImageReceiver {
    private static JFrame frame = new JFrame();

    //JDesktopPane represents the main container that will contain all connected clients' screens

    private static JDesktopPane desktop = new JDesktopPane();
    private static Socket cSocket = null;
    private static JInternalFrame interFrame = new JInternalFrame("Server Screen", true, true, true);
    private static JPanel cPanel = new JPanel();



    public static void main(String[] args) throws IOException {

        //startSendEvents();

        InetAddress multicastGroup = InetAddress.getByName("239.0.0.1"); // Multicast IP address
        int port = 1234;

        MulticastSocket multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(multicastGroup);
       // DatagramSocket datagramSocket =new DatagramSocket(1234);

        int maxBufferSize = 65507;

        frame.add(desktop, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Show thr frame in maximized state

        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);		//CHECK THIS LINE
        frame.setVisible(true);
        interFrame.setLayout(new BorderLayout());
        interFrame.getContentPane().add(cPanel, BorderLayout.CENTER);
        interFrame.setSize(100,100);
        desktop.add(interFrame);

        try {
            //Initially show the internal frame maximized
            interFrame.setMaximum(true);
        }catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }

        //This allows to handle KeyListener events
        cPanel.setFocusable(true);
        interFrame.setVisible(true);
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
                Image image = ImageIO.read(inStream);


                Graphics graphics = cPanel.getGraphics();
                graphics.drawImage(image, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);
//                SwingUtilities.invokeLater(() -> {
//                    imageLabel.setIcon(new ImageIcon(image));
//                    frame.pack();
//                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static void startSendEvents(){
        try {
            new SendEvents(new Socket("192.168.1.4", 1234), cPanel, "1920", "1080");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
