package com.pivotgroup.pivotlib.bootstrap;

import com.pivotgroup.pivotlib.core.Pivot;
import com.pivotgroup.pivotlib.device.awt.RobotImageCapturer;
import com.pivotgroup.pivotlib.device.awt.RobotTouchCapturer;

import java.awt.*;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/16 01:16
 */

public class DesktopPivot {

    private DesktopPivot(){}

    public static Pivot create() {
        Robot robot = createRobot();
        return new Pivot(new RobotTouchCapturer(robot), new RobotImageCapturer(robot));
    }

    private static Robot createRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new IllegalStateException(
                    "创建 AWT Robot 失败：当前可能没有桌面会话（headless 环境、Windows Session 0 或未登录）", e);
        }
    }
}
