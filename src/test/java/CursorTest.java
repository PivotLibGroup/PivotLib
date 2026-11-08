import com.pivotgroup.pivotlib.Pivot;
import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.screencapture.ScreenCapturer;
import com.pivotgroup.pivotlib.util.Anchor;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.MatchStep;
import com.pivotgroup.pivotlib.workflow.step.OperateMatchStep;
import org.junit.jupiter.api.Test;

public class CursorTest {

    @Test
    public void cursorTest() {
        ActionSimulator actionSimulator = new ActionSimulator(new RobotTouchPoint());
        ScreenCapturer screenCapturer = new ScreenCapturer(new RobotScreenCapturer());

        Pivot pivot = new Pivot(actionSimulator, screenCapturer);

        long startTime = System.currentTimeMillis();
        Workflow loginFlow = new Workflow("login");
        loginFlow.addStep(new MatchStep("src/test/resources/qq.png"));
        loginFlow.addStep(new OperateMatchStep(new OperateMatchStep.Swipe(-100, 0, 2000, 300), Anchor.CENTER));

        loginFlow.exec(actionSimulator, screenCapturer);

        System.out.println(System.currentTimeMillis() - startTime + "ms");
    }

}
