import com.pivotgroup.pivotlib.Pivot;
import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.util.Anchor;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.matchstep.MatchStep;
import com.pivotgroup.pivotlib.workflow.step.OperateMatchStep;
import org.junit.jupiter.api.Test;

public class CursorTest {

    @Test
    public void cursorTest()  {
        ActionSimulator actionSimulator = new ActionSimulator(new RobotTouchPoint());

        Pivot pivot = new Pivot(actionSimulator);

        Workflow loginFlow = new Workflow("login");
        loginFlow.addStep(new MatchStep("src/test/resources/qq.png"));
        loginFlow.addStep(new OperateMatchStep(new OperateMatchStep.Swipe(-100, 0, 0, 30), Anchor.CENTER));

        loginFlow.exec(actionSimulator);
    }

}
