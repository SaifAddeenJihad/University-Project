package Stream;

import org.xerial.snappy.Snappy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class
Capture implements Runnable{

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
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            Graphics g = image.getGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(mouse.x, mouse.y, 30, 30);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage courser ;
            try {
                courser=ImageIO.read(new File("C:\\Users\\Saif\\Downloads\\magic-cursor.cur"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                ImageIO.write(image, "jpeg", baos);
                byte[] fullBuffer = baos.toByteArray();
                byte[] compressed= new byte[0];
                compressed = Snappy.compress(fullBuffer);
                Main.baos.add(compressed);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Main.baos.size());
        }
    }
}
