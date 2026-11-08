import com.pivotgroup.pivotlib.actionsimulator.TouchPoint;

import java.awt.event.InputEvent;

public class RobotTouchPoint implements TouchPoint {


    @Override
    public void press() {
        RobotSingleton.getInstance().mousePress(InputEvent.BUTTON1_MASK);
    }

    @Override
    public void move(int x, int y) {
        RobotSingleton.getInstance().mouseMove(x, y);
    }

    @Override
    public void release() {
        RobotSingleton.getInstance().mouseRelease(InputEvent.BUTTON1_MASK);
    }
}
