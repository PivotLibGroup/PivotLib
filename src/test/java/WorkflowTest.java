import com.pivotgroup.pivotlib.action.CoordMode;
import com.pivotgroup.pivotlib.action.gesture.DoubleTap;
import com.pivotgroup.pivotlib.action.gesture.Swipe;
import com.pivotgroup.pivotlib.action.gesture.Tap;
import com.pivotgroup.pivotlib.action.simulator.ActionSimulator;
import com.pivotgroup.pivotlib.screencapture.ScreenCapturer;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.ActionStep;
import opencv.util.MatUtils;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 18:18
 * @description
 */

public class WorkflowTest {

    @Test
    public void test() {
        ActionSimulator mas = new ActionSimulator(new RobotTouchPoint());
        ScreenCapturer sc = new ScreenCapturer(new RobotScreenCapturer());
        RobotScreenCapturer rsc = new RobotScreenCapturer();

        Mat qqMat = MatUtils.loadImage("src/test/resources/qq.png");
        Mat trashcanMat = MatUtils.loadImage("src/test/resources/trashcan.png");
        Mat testFileMat = MatUtils.loadImage("src/test/resources/test_file.png");

        Workflow lwf = new Workflow("login", mas, sc);
        //lwf.addStep(new ActionStep(new Tap(rsc.capture(), qqMat, 500)));

        // lwf.addStep(new ActionStep(new DoubleTap(rsc.capture(), qqMat, 500)));

       lwf.addStep(new ActionStep(new Swipe(rsc.capture(),
                qqMat,
                -150,
                0,
                CoordMode.RELATIVE,
                1000,
                100)));

        lwf.exec(mas, sc);
    }

}
