import com.pivotgroup.pivotlib.actionsimulator.MouseActionSimulator;
import com.pivotgroup.pivotlib.actionsimulator.touchpoint.MouseButton;
import com.pivotgroup.pivotlib.screencapture.ScreenCapturer;
import com.pivotgroup.pivotlib.util.Anchor;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.FindImageStep;
import com.pivotgroup.pivotlib.workflow.step.WaitStep;
import com.pivotgroup.pivotlib.workflow.step.action.OperateFindStep;
import com.pivotgroup.pivotlib.workflow.step.action.Swipe;
import com.pivotgroup.pivotlib.workflow.step.action.mouse.MouseTapStep;
import org.junit.jupiter.api.Test;

public class CursorTest {

    @Test
    public void cursorTest() {
        // 鼠标动作模拟器
        MouseActionSimulator mas = new MouseActionSimulator(new RobotMouseTouchPoint());

        ScreenCapturer screenCapturer = new ScreenCapturer(new RobotScreenCapturer());

        Workflow loginFlow = new Workflow("login");
        for (int i = 0; i < 5; i++) {
            loginFlow.addStep(new FindImageStep("src/test/resources/qq.png"));
            loginFlow.addStep(new OperateFindStep(new Swipe(-150,0,2000,100), Anchor.CENTER));
            loginFlow.addStep(new WaitStep(500));
        }

        for (int i = 0; i < 5; i++) {
            loginFlow.addStep(new FindImageStep("src/test/resources/qq.png"));
            loginFlow.addStep(new OperateFindStep(new Swipe(150,0,2000,100), Anchor.CENTER));
            loginFlow.addStep(new WaitStep(500));
        }

        loginFlow.exec(mas, screenCapturer);
    }

}
