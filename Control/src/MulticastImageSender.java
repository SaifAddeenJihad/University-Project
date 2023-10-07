import org.xerial.snappy.Snappy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class MulticastImageSender {
    public static void main(String[] args) throws IOException, AWTException, InterruptedException {

        MulticastSocket multicastSocket = new MulticastSocket();
        InetAddress multicastGroup = InetAddress.getByName("239.0.0.1"); // Multicast IP address

        Robot robot = new Robot();
        Rectangle capture = new Rectangle(1920, 1080);

        while (true) {
            BufferedImage image = robot.createScreenCapture(capture);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpeg", baos);
            byte[] fullBuffer = baos.toByteArray();
            fullBuffer = Snappy.compress(fullBuffer);
            int chunkSize = 65507;
            int totalChunks = (int) Math.ceil((double) fullBuffer.length / chunkSize);

            for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
                int offset = chunkIndex * chunkSize;
                int length = Math.min(chunkSize, fullBuffer.length - offset);
                byte[] chunkBuffer = new byte[length];
                System.arraycopy(fullBuffer, offset, chunkBuffer, 0, length);

                DatagramPacket datagramPacket = new DatagramPacket(chunkBuffer, chunkBuffer.length, multicastGroup, 1234);
                multicastSocket.send(datagramPacket);
            }

        }
    }
}
