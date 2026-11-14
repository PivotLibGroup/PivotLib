import com.pivotgroup.pivotlib.action.OperationSimulator;
import com.pivotgroup.pivotlib.action.ClickOperation;
import com.pivotgroup.pivotlib.action.MoveOperation;
import com.pivotgroup.pivotlib.core.Pivot;
import org.junit.jupiter.api.Test;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 18:47
 */

public class BasicOperationTest {

    @Test
    public void test(){
        OperationSimulator os = new OperationSimulator(new RobotTouchCapturer());
        Pivot pivot = new Pivot(os);
        pivot.addOperation(new ClickOperation(1, 50));
        pivot.addOperation(new MoveOperation(500,500,1000, MoveOperation.Type.RELATIVE,100));
        pivot.start();
    }

}