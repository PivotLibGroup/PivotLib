import com.pivotgroup.pivotlib.ActionSimulator;
import com.pivotgroup.pivotlib.Pivot;
import com.pivotgroup.pivotlib.util.MatUtils;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.*;
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
        loginFlow.addStep(new TapMatchedStep());

        loginFlow.exec(actionSimulator);
    }

    @Test
    public void robotTest() throws AWTException {

    }
}
