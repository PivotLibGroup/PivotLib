package com.pivotgroup.pivotlib.capturer.awt;

import java.awt.*;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class RobotSingleton {

    private static Robot robot;

    public static Robot getInstance() {
        if (robot == null) {
            robot = createRobot();
        }
        return robot;
    }

    private static Robot createRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

}
