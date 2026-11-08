import com.pivotgroup.pivotlib.Pivot;
import com.pivotgroup.pivotlib.actionsimulator.MouseActionSimulator;
import com.pivotgroup.pivotlib.actionsimulator.SimpleActionSimulator;
import com.pivotgroup.pivotlib.screencapture.ScreenCapturer;
import com.pivotgroup.pivotlib.util.Anchor;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.MatchStep;
import com.pivotgroup.pivotlib.workflow.step.OperateMatchStep;
import org.junit.jupiter.api.Test;

public class CursorTest {

    @Test
    public void cursorTest() {
        // 鼠标动作模拟器
        MouseActionSimulator mas = new MouseActionSimulator(new RobotMouseTouchPoint());

        ScreenCapturer screenCapturer = new ScreenCapturer(new RobotScreenCapturer());

        Workflow loginFlow = new Workflow("login");
        loginFlow.addStep(new MatchStep("src/test/resources/qq.png"));
        loginFlow.addStep(new OperateMatchStep(new OperateMatchStep.Swipe(-100, 0, 2000, 300), Anchor.CENTER));

        loginFlow.exec(mas, screenCapturer);
    }

}
