package com.pivotgroup.pivotlib.device.robot;

import java.awt.AWTException;
import java.awt.Robot;


public final class RobotSingleton {

    private static final Robot INSTANCE = create();

    private RobotSingleton() {
    }

    private static Robot create() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new IllegalStateException("创建 AWT Robot 失败，当前环境可能不支持桌面操作", e);
        }
    }

    public static Robot getInstance() {
        return INSTANCE;
    }
}
