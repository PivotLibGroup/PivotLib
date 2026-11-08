package com.pivotgroup.pivotlib.actionsimulator;

import com.pivotgroup.pivotlib.actionsimulator.touchpoint.MouseButton;
import com.pivotgroup.pivotlib.actionsimulator.touchpoint.MouseTouchPoint;

/**
 * 鼠标动作模拟器，支持多按键操作
 */
public class MouseActionSimulator implements ActionSimulator {

    private final MouseTouchPoint mouseTouchPoint;

    public MouseActionSimulator(MouseTouchPoint mouseTouchPoint) {
        this.mouseTouchPoint = mouseTouchPoint;
    }

    public void down(MouseButton button) {
        mouseTouchPoint.press(button);
    }

    public void up(MouseButton button) {
        mouseTouchPoint.release(button);
    }

    public void tap(int x, int y, MouseButton button, long duration) {
        move(x, y);
        down(button);
        sleep(duration);
        up(button);
    }

    public void swipe(int startX, int startY, int endX, int endY, MouseButton button, long totalDuration, int steps) {
        if (steps <= 0) steps = 1;
        long stepDelay = totalDuration / steps;

        move(startX, startY);
        down(button);

        for (int i = 1; i <= steps; i++) {
            int x = startX + (endX - startX) * i / steps;
            int y = startY + (endY - startY) * i / steps;
            sleep(stepDelay);
            move(x, y);
        }

        up(button);
    }

    @Override
    public void down() {
        mouseTouchPoint.press(MouseButton.LEFT);
    }

    @Override
    public void move(int x, int y) {
        mouseTouchPoint.move(x, y);
    }

    @Override
    public void up() {
        mouseTouchPoint.release(MouseButton.LEFT);
    }

    @Override
    public void tap(int x, int y, long duration) {
        tap(x, y, MouseButton.LEFT, duration);
    }

    @Override
    public void swipe(int startX, int startY, int endX, int endY, long totalDuration, int steps) {
        swipe(startX, startY, endX, endY, MouseButton.LEFT, totalDuration, steps);
    }

    @Override
    public void sleep(long ms) {
        if (ms <= 0) return;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
