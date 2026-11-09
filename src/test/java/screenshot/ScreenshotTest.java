package screenshot;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenshotTest {

    @Test
    public void example() throws AWTException, IOException {
        Robot robot = new Robot();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(d);
        Rectangle screenRect = new Rectangle(d);
        BufferedImage bufferedImage = robot.createScreenCapture(screenRect);
        File file = new File("build/截图1.png");
        ImageIO.write(bufferedImage, "png", file);
    }


}
