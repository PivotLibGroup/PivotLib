import com.pivotgroup.pivotlib.action.operation.ClickOperation;
import com.pivotgroup.pivotlib.core.Pivot;
import nu.pattern.OpenCV;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 18:47
 */

public class OperationTest {

    Mat qqMat = readMat("src/test/resources/qq.png");
    Pivot pivot = new Pivot(new RobotTouchCapturer(), new RobotImageCapturer());

    static {
        OpenCV.loadLocally();
    }

    @Test
    public void clickTest() {
        // 原地点击 完成
        /*pivot.addOperation(new ClickOperation(3, 50));*/

        // 点击绝对值位置 完成
        /* pivot.addOperation(new ClickOperation(
                () -> new Location(700, 500),
                3,
                50));*/

        // 识别qqMat位置后点击 完成
        /*pivot.addOperation(new ClickOperation(
                () -> pivot.locate(qqMat),
                3,
                50));*/

        // 偏移量点击 完成
        /*pivot.addOperation(new ClickOperation(
                () -> pivot.locate(qqMat),
                3,
                50));*/

        pivot.start();
    }

    @Test
    public void moveTest() {
        // 以绝对坐标的方式移动到500:500的位置 完成
        /*pivot.addOperation(new MoveOperation(
                () -> new Location(500, 500),
                CoordMode.ABSOLUTE,
                1000,
                100));*/

        // 移动到识别到的图像位置 完成
        /*pivot.addOperation(new MoveOperation(
                () -> pivot.locate(qqMat),
                CoordMode.ABSOLUTE,
                1000,
                100));*/

        // 移动随机位置, 设置偏移 完成
        /*pivot.addOperation(new MoveOperation(
                () -> new Location(100,100).offset(-100,-100),
                CoordMode.ABSOLUTE,
                1000,
                100));*/

        // 以相对坐标的方式, 使鼠标向左上角移动50个像素 完成
        /*pivot.addOperation(new MoveOperation(
                () -> new Location(-50,-50),
                CoordMode.RELATIVE,
                1000,
                100));*/

        // 采用直接移动的方式移动到目标位置 完成
        /*pivot.addOperation(new MoveOperation(
                () -> new Location(-50,-50),
                CoordMode.RELATIVE));*/

        pivot.start();
    }

    @Test
    public void dragTest() {

        pivot.start();
    }

    private Mat readMat(final String path) {
        return Imgcodecs.imread(path);
    }

}