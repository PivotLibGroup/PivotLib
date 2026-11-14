import com.pivotgroup.pivotlib.action.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/14 20:45
 */

public class RobotTouchCapturer implements TouchCapturable {

    private Robot robot;

    public RobotTouchCapturer() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

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
        Point l = MouseInfo.getPointerInfo().getLocation();
        return new Location(l.x, l.y);
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
