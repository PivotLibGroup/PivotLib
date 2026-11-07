import com.pivotgroup.pivotlib.actionsimulator.TouchPoint;

import java.awt.*;
import java.awt.event.InputEvent;

public class RobotTouchPoint implements TouchPoint {

    public static final Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void press() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
    }

    @Override
    public void move(int x, int y) {
        robot.mouseMove(x, y);
    }

    @Override
    public void release() {
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
}
