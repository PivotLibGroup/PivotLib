import com.pivotgroup.pivotlib.actionsimulator.MouseActionSimulator;
import com.pivotgroup.pivotlib.screencapture.ScreenCapturer;
import com.pivotgroup.pivotlib.util.MatUtils;
import com.pivotgroup.pivotlib.util.TapType;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.WaitStep;
import com.pivotgroup.pivotlib.workflow.step.action.BiFindSwipeStep;
import com.pivotgroup.pivotlib.workflow.step.action.MatchTapStep;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;

public class CursorTest {

    private final MouseActionSimulator mas = new MouseActionSimulator(new RobotMouseTouchPoint());

    private final ScreenCapturer screenCapturer = new ScreenCapturer(new RobotScreenCapturer());

    @Test
    public void cursorTest() {
        // 鼠标动作模拟器
        Workflow loginFlow = new Workflow("login");

        Mat mat = MatUtils.loadImage("src/test/resources/qq.png");
        loginFlow.addStep(new MatchTapStep(mat, TapType.DOUBLE_TAP_TYPE, 200));
        loginFlow.addStep(new WaitStep(200));

        loginFlow.exec(mas, screenCapturer);
    }

    @Test
    public void biFindSwipeStepTest() {
        Workflow dropFlow = new Workflow("drop");
        for (int i = 0; i < 7; i++) {
            dropFlow.addStep(new BiFindSwipeStep(MatUtils.loadImage("src/test/resources/test_file.png"), MatUtils.loadImage("src/test/resources/trashcan.png")));
        }

        dropFlow.exec(mas, screenCapturer);
    }

}
