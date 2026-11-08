import com.pivotgroup.pivotlib.actionsimulator.CoordMode;
import com.pivotgroup.pivotlib.actionsimulator.MouseActionSimulator;
import com.pivotgroup.pivotlib.actionsimulator.action.Swipe;
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
        MouseActionSimulator mas = new MouseActionSimulator(new RobotMouseTouchPoint());
        ScreenCapturer sc = new ScreenCapturer(new RobotScreenCapturer());
        RobotScreenCapturer rsc = new RobotScreenCapturer();

        Mat qqMat = MatUtils.loadImage("src/test/resources/qq.png");
        Mat trashcanMat = MatUtils.loadImage("src/test/resources/trashcan.png");
        Mat testFileMat = MatUtils.loadImage("src/test/resources/test_file.png");
        // 理想的结构...
        Workflow lwf = new Workflow("login");
        // 1.找到登录按钮并点击
        // 点击 100:100 位置
        //lwf.addStep(new ActionStep(new Tap(100, 100, 2000)));
        // 从100：100拖动到200：200位置，持续时间2秒
        //lwf.addStep(new ActionStep(new Swipe(100, 100, 200, 200, 2000, 30)));
        //lwf.addStep(new ActionStep(new DoubleTap(100, 100, 500)));
        //lwf.addStep(new WaitStep(10000));

        lwf.addStep(new ActionStep(new Swipe(rsc.capture(),
                qqMat,
                -150,
                0,
                CoordMode.RELATIVE,
                10000,
                100)));

        lwf.exec(mas, sc);
    }

}
