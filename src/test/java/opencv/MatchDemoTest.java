package opencv;

import opencv.util.MatUtils;
import org.junit.jupiter.api.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class MatchDemoTest {

    @Test
    public void matchDemo() {
        Mat screen = MatUtils.loadImage("src/test/resources/tx.jpg");

        Mat template = new Mat(screen, new Rect(480, 160, 110, 120)).clone();
        MatUtils.saveImage(template, "build/1-template.png");

        Mat scores = new Mat();
        Imgproc.matchTemplate(screen, template, scores, Imgproc.TM_CCOEFF_NORMED);

        Core.MinMaxLocResult best = Core.minMaxLoc(scores);
        int x = (int) best.maxLoc.x;
        int y = (int) best.maxLoc.y;

        Imgproc.rectangle(screen, new Point(x, y),
                new Point(x + template.cols(), y + template.rows()),
                new Scalar(0, 0, 255), 4);
        Imgcodecs.imwrite("build/2-result.png", screen);
    }
}
