import com.pivotgroup.pivotlib.action.ClickOperation;
import com.pivotgroup.pivotlib.action.MoveOperation;
import com.pivotgroup.pivotlib.action.OperationSimulator;
import com.pivotgroup.pivotlib.core.Pivot;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.ResultLoc;
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

    static{
        OpenCV.loadLocally();
    }

    @Test
    public void test() {
        Mat qqMat = readMat("src/test/resources/qq.png");

        OperationSimulator os = new OperationSimulator(new RobotTouchCapturer());
        RobotImageCapturer ric = new RobotImageCapturer();
        Pivot pivot = new Pivot(os, ric);

        // 原地点击
        pivot.addOperation(new ClickOperation(1, 50));

        // 移动到500:500的位置
        pivot.addOperation(new MoveOperation(500, 500, 1000, MoveOperation.Type.RELATIVE, 100));

        // 移动到qq图像上
        ResultLoc rl = pivot.identify(qqMat);
        Location loc = rl.loc;
        pivot.addOperation(new MoveOperation(loc.x,loc.y, 1000, MoveOperation.Type.RELATIVE, 100));

        // 原地点击
        pivot.addOperation(new ClickOperation(1, 50));

        pivot.start();
    }

    private Mat readMat(String path){
        return Imgcodecs.imread(path);
    }

}