import com.pivotgroup.pivotlib.vision.ImageCapturable;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/14 22:28
 */

public class RobotImageCapturer implements ImageCapturable {

    private Robot robot;

    public RobotImageCapturer() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mat capture() {
        Toolkit dt = Toolkit.getDefaultToolkit();
        Dimension ss = dt.getScreenSize();
        return capture(0, 0, (int) ss.getHeight(), (int) ss.getWidth());
    }

    @Override
    public Mat capture(int x, int y, int width, int height) {
        BufferedImage sc = robot.createScreenCapture(new Rectangle(x, y, width, height));
        return bufferedImageToMat(sc);
    }

    public static Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

}
