package com.pivotgroup.pivotlib.capturer.awt;

import com.pivotgroup.pivotlib.action.KeyboardCapturer;

import java.awt.*;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class AwtKeyboardCapturer implements KeyboardCapturer {

    private final Robot robot = RobotSingleton.getInstance();

    @Override
    public void keyPress(int keycode) {
        robot.keyPress(keycode);
    }
}
