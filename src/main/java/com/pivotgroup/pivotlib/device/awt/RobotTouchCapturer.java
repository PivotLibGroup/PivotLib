package com.pivotgroup.pivotlib.device.awt;

import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.Objects;

/**
 * AWT Robot implementation of pointer input.
 *
 * @author xinliu
 */
public class RobotTouchCapturer implements TouchCapturable {

    private final Robot robot;

    public RobotTouchCapturer(Robot robot) {
        this.robot = Objects.requireNonNull(robot, "robot");
    }

    @Override
    public void down(int button) {
        robot.mousePress(MouseButtonMapper.toAwtMask(button));
    }

    @Override
    public void release(int button) {
        robot.mouseRelease(MouseButtonMapper.toAwtMask(button));
    }

    @Override
    public void move(int x, int y) {
        robot.mouseMove(x, y);
    }

    @Override
    public Location getLocation() {
        Point location = MouseInfo.getPointerInfo().getLocation();
        return Location.of(location.x, location.y);
    }
}
