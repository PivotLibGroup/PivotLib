package com.pivotgroup.pivotlib_old.device.robot;

import com.pivotgroup.pivotlib_old.input.InputButton;
import com.pivotgroup.pivotlib_old.input.InputDevice;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class RobotInputDevice implements InputDevice {

    private final Robot robot;

    public RobotInputDevice() {
        this(RobotSingleton.getInstance());
    }

    public RobotInputDevice(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void move(int x, int y) {
        robot.mouseMove(x, y);
    }

    @Override
    public void press(InputButton button) {
        robot.mousePress(mask(button));
    }

    @Override
    public void release(InputButton button) {
        robot.mouseRelease(mask(button));
    }

    @Override
    public void scroll(int clicks) {
        robot.mouseWheel(clicks);
    }

    private static int mask(InputButton button) {
        return switch (button) {
            case LEFT -> InputEvent.BUTTON1_DOWN_MASK;
            case RIGHT -> InputEvent.BUTTON3_DOWN_MASK;
            case MIDDLE -> InputEvent.BUTTON2_DOWN_MASK;
        };
    }
}
