package com.pivotgroup.pivotlib.actionsimulator;

import com.pivotgroup.pivotlib.actionsimulator.touchpoint.TouchPoint;

public class SimpleActionSimulator implements ActionSimulator {

    private final TouchPoint touchPoint;

    public SimpleActionSimulator(TouchPoint touchPoint) {
        this.touchPoint = touchPoint;
    }

    public void down() {
        touchPoint.press();
    }

    public void move(int x, int y) {
        touchPoint.move(x, y);
    }

    public void up() {
        touchPoint.release();
    }

    public void tap(int x, int y, long duration) {
        move(x, y);
        down();
        sleep(duration);
        up();
    }

    public void swipe(int startX, int startY, int endX, int endY, long totalDuration, int steps) {
        if (steps <= 0) steps = 1;
        long stepDelay = totalDuration / steps;

        move(startX, startY);
        down();

        for (int i = 1; i <= steps; i++) {
            int x = startX + (endX - startX) * i / steps;
            int y = startY + (endY - startY) * i / steps;
            sleep(stepDelay);
            move(x, y);
        }

        up();
    }

    public void sleep(long ms) {
        if (ms <= 0) return;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
