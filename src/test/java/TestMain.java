import com.pivotgroup.pivotlib.ActionSimulator;
import com.pivotgroup.pivotlib.Pivot;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.SwipeStep;
import com.pivotgroup.pivotlib.workflow.step.TapStep;
import com.pivotgroup.pivotlib.workflow.step.WaitStep;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.InputEvent;

public class TestMain {

    @Test
    public void main() throws AWTException {
        Robot robot = new Robot();

        ActionSimulator actionSimulator = new ActionSimulator();
        actionSimulator.mountDown(() -> robot.mousePress(InputEvent.BUTTON1_MASK));
        actionSimulator.mountMove((x, y) -> robot.mouseMove(x, y));
        actionSimulator.mountUp(() -> robot.mouseRelease(InputEvent.BUTTON1_MASK));

        Pivot pivot = new Pivot(actionSimulator);

        Workflow loginFlow = new Workflow("login");
        loginFlow.addStep(new TapStep(500, 800, 50));
        loginFlow.addStep(new WaitStep(1000));
        loginFlow.addStep(new SwipeStep(600, 200, 600, 500, 300, 20));
        loginFlow.addStep(new WaitStep(500));

        loginFlow.exec(actionSimulator);
    }

    @Test
    public void robotTest() throws AWTException {

    }
}
