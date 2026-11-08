import com.pivotgroup.pivotlib.actionsimulator.touchpoint.MouseButton;
import com.pivotgroup.pivotlib.actionsimulator.touchpoint.MouseTouchPoint;

import java.awt.event.InputEvent;

public class RobotMouseTouchPoint implements MouseTouchPoint {

    @Override
    public void press(MouseButton button) {
        RobotSingleton.getInstance().mousePress(mapToRobotMask(button));
    }

    @Override
    public void release(MouseButton button) {
        RobotSingleton.getInstance().mouseRelease(mapToRobotMask(button));
    }

    @Override
    public void scroll(int delta) {
        RobotSingleton.getInstance().mouseWheel(delta);
    }

    @Override
    public void move(int x, int y) {
        RobotSingleton.getInstance().mouseMove(x, y);
    }

    private int mapToRobotMask(MouseButton button) {
        switch (button) {
            case LEFT:
                return InputEvent.BUTTON1_MASK;
            case RIGHT:
                return InputEvent.BUTTON3_MASK;
            case MIDDLE:
                return InputEvent.BUTTON2_MASK;
            default:
                return InputEvent.BUTTON1_MASK;
        }
    }
}
