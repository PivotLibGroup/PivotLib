import com.pivotgroup.pivotlib.action.OperationSimulator;
import com.pivotgroup.pivotlib.action.TouchCapturer;
import com.pivotgroup.pivotlib.action.operation.ClickOperation;
import com.pivotgroup.pivotlib.action.operation.MoveOperation;
import com.pivotgroup.pivotlib.core.Pivot;
import com.pivotgroup.pivotlib.model.Location;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 18:47
 */

public class BasicOperationTest {

    @Test
    public void test() throws AWTException {
        Pivot pivot = new Pivot(generateOs());
        pivot.addOperation(new ClickOperation(1, 50));
        pivot.addOperation(new MoveOperation(10,10,1000, MoveOperation.Type.RELATIVE,100));
        pivot.start();
    }

    private OperationSimulator generateOs() {
        try {
            Robot robot = new Robot();
            Point l = MouseInfo.getPointerInfo().getLocation();
            OperationSimulator os = new OperationSimulator(new TouchCapturer() {
                @Override
                public void down(int button) {
                    robot.mousePress(mapperButton(button));
                }

                @Override
                public void release(int button) {
                    robot.mouseRelease(mapperButton(button));
                }

                @Override
                public void move(int x, int y) {
                    robot.mouseMove(x, y);
                }

                @Override
                public Location getLocation() {
                    return new Location(l.x, l.y);
                }
            });
            return os;
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private int mapperButton(int button) {
        switch (button) {
            case 1:
                return InputEvent.BUTTON1_DOWN_MASK;
            case 2:
                return InputEvent.BUTTON2_DOWN_MASK;
            case 3:
                return InputEvent.BUTTON3_DOWN_MASK;
            default:
                return InputEvent.BUTTON1_DOWN_MASK;
        }
    }

}