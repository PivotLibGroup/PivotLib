import com.pivotgroup.pivotlib.Pivot;
import com.pivotgroup.pivotlib.simulator.ActionSimulator;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.MatchStep;
import com.pivotgroup.pivotlib.workflow.step.OperateMatchStep;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.InputEvent;

public class CursorTest {

    @Test
    public void main() throws AWTException {
        Robot robot = new Robot();

        ActionSimulator actionSimulator = new ActionSimulator();
        actionSimulator.mountDown(() -> robot.mousePress(InputEvent.BUTTON1_MASK));
        actionSimulator.mountMove((x, y) -> robot.mouseMove(x, y));
        actionSimulator.mountUp(() -> robot.mouseRelease(InputEvent.BUTTON1_MASK));

        Pivot pivot = new Pivot(actionSimulator);

        Workflow loginFlow = new Workflow("login");
        loginFlow.addStep(new MatchStep("src/test/resources/qq.png"));
        loginFlow.addStep(new OperateMatchStep(new OperateMatchStep.Swipe(-100, 0, 0, 30)));

        loginFlow.exec(actionSimulator);
    }

}
