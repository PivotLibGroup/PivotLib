import com.pivotgroup.pivotlib.vision.ImageCapturable;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Objects;

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
        return capture(0, 0, ss.width, ss.height);
    }

    @Override
    public Mat capture(int x, int y, int width, int height) {
        BufferedImage sc = robot.createScreenCapture(new Rectangle(x, y, width, height));
        return bufferedImageToMat(sc);
    }

    private static BufferedImage toBufferedImage(Mat mat) {
        return (BufferedImage) HighGui.toBufferedImage(mat);
    }

    private static Mat bufferedImageToMat(BufferedImage bi) {
        Objects.requireNonNull(bi);
        if (bi.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            BufferedImage bgr = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g = bgr.createGraphics();
            try {
                g.setComposite(AlphaComposite.Src);
                g.drawImage(bi, 0, 0, null);
            } finally {
                g.dispose();
            }
            bi = bgr;
        }

        final byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }

}
