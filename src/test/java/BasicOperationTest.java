import com.pivotgroup.pivotlib.action.ClickOperation;
import com.pivotgroup.pivotlib.action.DragOperation;
import com.pivotgroup.pivotlib.action.MoveOperation;
import com.pivotgroup.pivotlib.core.Pivot;
import com.pivotgroup.pivotlib.model.Location;
import nu.pattern.OpenCV;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 18:47
 */

public class BasicOperationTest {

    static {
        OpenCV.loadLocally();
    }

    @Test
    public void test() {
        Mat qqMat = readMat("src/test/resources/qq.png");
        ClickOperation normalClick = new ClickOperation(1, 50);

        Pivot pivot = new Pivot(new RobotTouchCapturer(), new RobotImageCapturer());

        // 原地点击
        pivot.addOperation(new ClickOperation(1, 50));

        pivot.addOperation(new ClickOperation(() -> pivot.locate(qqMat), 1, 50));

        // 移动到500:500的位置
        pivot.addOperation(new MoveOperation(
                () -> new Location(500, 500),
                MoveOperation.Type.RELATIVE,
                1000,
                100));

        /*
        // 移动到qq图像上
        pivot.identify(qqMat).computeIfPresent((location) ->
                pivot.addOperation(new MoveOperation(location, 1000, MoveOperation.Type.RELATIVE, 100))
        );
         */

        // 移动到qq图标位置
        pivot.addOperation(new MoveOperation(
                () -> pivot.locate(qqMat),
                MoveOperation.Type.RELATIVE,
                1000,
                100));

        // 移动qq图标向左150像素
        pivot.addOperation(new DragOperation(
                () -> pivot.locate(qqMat),
                () -> new Location(10, 10),
                1,
                1000,
                100
        ));

        // 原地点击
        pivot.addOperation(normalClick);

        pivot.start();
    }

    private Mat readMat(String path) {
        return Imgcodecs.imread(path);
    }

}