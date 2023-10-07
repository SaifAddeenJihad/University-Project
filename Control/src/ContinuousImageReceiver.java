import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.Iterator;

public class ContinuousImageReceiver {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(1234);

        int maxBufferSize = 65507;

        JFrame frame = new JFrame("Continuous Image Receiver");
        JLabel imageLabel = new JLabel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(imageLabel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while (true) {
            byte[] fullBuffer = new byte[0];


            byte[] chunk = new byte[maxBufferSize];
            DatagramPacket datagramPacket = new DatagramPacket(chunk, maxBufferSize);
            datagramSocket.receive(datagramPacket);

            byte[] receivedData = datagramPacket.getData();
            int receivedLength = datagramPacket.getLength();

            // Concatenate received chunk to the full buffer
            byte[] newBuffer = new byte[fullBuffer.length + receivedLength];
            System.arraycopy(fullBuffer, 0, newBuffer, 0, fullBuffer.length);
            System.arraycopy(receivedData, 0, newBuffer, fullBuffer.length, receivedLength);
            fullBuffer = newBuffer;




            try {
                InputStream inStream = new ByteArrayInputStream(fullBuffer);

                Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

                if (!writers.hasNext()) {
                    throw new IllegalStateException("No writers found");
                }

                ImageWriter writer = (ImageWriter) writers.next();



                ImageOutputStream ios = ImageIO.createImageOutputStream(inStream);

                writer.setOutput(ios);

                ImageWriteParam param = writer.getDefaultWriteParam();

                writer.write(null, new IIOImage(ImageIO.read(inStream), null, null), param);

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
