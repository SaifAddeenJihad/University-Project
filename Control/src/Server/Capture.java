package Server;

import Server.Main;
import org.xerial.snappy.Snappy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Capture implements Runnable{

    @Override
    public void run() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        Rectangle capture = new Rectangle(1920, 1080);
        while (true) {

            BufferedImage image = robot.createScreenCapture(capture);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, "jpeg", baos);
                byte[] fullBuffer = baos.toByteArray();
                byte[] compressed= new byte[0];
                compressed = Snappy.compress(fullBuffer);
                Main.baos.add(compressed);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
