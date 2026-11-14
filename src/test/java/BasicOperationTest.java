import com.pivotgroup.pivotlib.action.ClickOperation;
import com.pivotgroup.pivotlib.action.MoveOperation;
import com.pivotgroup.pivotlib.action.OperationSimulator;
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

public class BasicOperationTest {

    static{
        OpenCV.loadLocally();
    }

    @Test
    public void test() {
        OperationSimulator os = new OperationSimulator(new RobotTouchCapturer());
        RobotImageCapturer ric = new RobotImageCapturer();
        Pivot pivot = new Pivot(os, ric);
        pivot.addIdentified(readMat("src/test/resources/qq.png"));
        pivot.addOperation(new ClickOperation(1, 50));
        pivot.addOperation(new MoveOperation(500, 500, 1000, MoveOperation.Type.RELATIVE, 100));
        pivot.start();

    }

    private Mat readMat(String path){
        return Imgcodecs.imread(path);
    }

}