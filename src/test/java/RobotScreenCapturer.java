import com.pivotgroup.pivotlib.screencapture.Capturer;
import com.pivotgroup.pivotlib.util.MatUtils;
import org.opencv.core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 09:08
 * @description
 */

public class RobotScreenCapturer implements Capturer {

    @Override
    public Mat capture() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        BufferedImage img = RobotSingleton.getInstance().createScreenCapture(new Rectangle(d));
        return MatUtils.bufferedImageToMat(img);
    }


}
