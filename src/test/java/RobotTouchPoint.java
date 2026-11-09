import com.pivotgroup.pivotlib.action.simulator.TouchButton;
import com.pivotgroup.pivotlib.action.simulator.TouchPoint;

import java.awt.event.InputEvent;


public class RobotTouchPoint implements TouchPoint {

    @Override
    public void press(TouchButton button) {
        RobotSingleton.getInstance().mousePress(mapToRobotMask(button));
    }

    @Override
    public void move(int x, int y) {
        RobotSingleton.getInstance().mouseMove(x, y);
    }

    @Override
    public void release(TouchButton button) {
        RobotSingleton.getInstance().mouseRelease(mapToRobotMask(button));
    }

    @Override
    public void scroll(int delta) {
        RobotSingleton.getInstance().mouseWheel(delta);
    }

    private int mapToRobotMask(TouchButton button) {
        return switch (button) {
            case LEFT -> InputEvent.BUTTON1_MASK;
            case RIGHT -> InputEvent.BUTTON3_MASK;
            case MIDDLE -> InputEvent.BUTTON2_MASK;
        };
    }
}
