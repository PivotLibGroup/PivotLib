package com.pivotgroup.pivotlib.capturer.awt;

import com.pivotgroup.pivotlib.action.TouchCapturer;

import java.awt.*;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class AwtTouchCapturer implements TouchCapturer {

    private Robot robot = RobotSingleton.getInstance();

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

}
