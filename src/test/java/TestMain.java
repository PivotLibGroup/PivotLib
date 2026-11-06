import com.pivotgroup.pivotlib.ActionSimulator;
import com.pivotgroup.pivotlib.Pivot;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.SwipeStep;
import com.pivotgroup.pivotlib.workflow.step.TapStep;
import com.pivotgroup.pivotlib.workflow.step.WaitStep;
import org.junit.jupiter.api.Test;

public class TestMain {

    @Test
    public void main() {
        // 1. 创建动作模拟器，挂载三个原子执行器（这里用打印模拟，实际接PC Robot / Android ADB）
        ActionSimulator actionSimulator = new ActionSimulator();
        actionSimulator.mountDown(() -> System.out.println("  [DOWN]"));
        actionSimulator.mountMove((x, y) -> System.out.println("  [MOVE] -> (" + x + ", " + y + ")"));
        actionSimulator.mountUp(() -> System.out.println("  [UP]"));

        // 2. 创建中枢
        Pivot pivot = new Pivot(actionSimulator);

        // 3. 组装一个工作流：点击登录按钮 → 等待 → 滑动界面
        Workflow loginFlow = new Workflow("login");
        loginFlow.addStep(new TapStep(500, 800, 50));    // 点击(500,800)，按下50ms
        loginFlow.addStep(new WaitStep(1000));            // 等待1秒
        loginFlow.addStep(new SwipeStep(100, 1500, 100, 500, 300, 20)); // 从下往上滑，300ms分20步
        loginFlow.addStep(new WaitStep(500));              // 再等500ms

        // 4. 执行
        loginFlow.exec(actionSimulator);
    }
}
