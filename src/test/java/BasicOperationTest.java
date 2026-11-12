import com.pivotgroup.pivotlib.action.OperationSimulator;
import com.pivotgroup.pivotlib.action.TouchCapturer;
import com.pivotgroup.pivotlib.core.Pivot;
import com.pivotgroup.pivotlib.model.Location;
import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 18:47
 */

public class BasicOperationTest {

    @Test
    public void test() throws AWTException {
        Robot robot = new Robot();
        Point l = MouseInfo.getPointerInfo().getLocation();
        OperationSimulator os = new OperationSimulator(new TouchCapturer() {
            @Override
            public void down(int button) {
                robot.mousePress(button);
            }

            @Override
            public void release(int button) {
                robot.mouseRelease(button);
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

        Pivot pivot = new Pivot(os);
        pivot.start();
    }

}