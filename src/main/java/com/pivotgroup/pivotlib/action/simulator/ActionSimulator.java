package com.pivotgroup.pivotlib.action.simulator;

public class ActionSimulator {

    private final TouchPoint touchPoint;

    public ActionSimulator(TouchPoint touchPoint) {
        this.touchPoint = touchPoint;
    }

    public void down(){
        down(TouchButton.LEFT);
    }

    public void down(TouchButton button) {
        touchPoint.press(button);
    }

    public void up() {
        up(TouchButton.LEFT);
    }

    public void up(TouchButton button) {
        touchPoint.release(button);
    }

    public void tap(int x, int y,  long duration) {
        tap(x,y,TouchButton.LEFT,duration);
    }

    public void tap(int x, int y, TouchButton button, long duration) {
        move(x, y);
        down(button);
        sleep(duration);
        up(button);
    }

    public void swipe(int startX, int startY, int endX, int endY, long totalDuration, int steps) {
        swipe(startX,startY,endX,endY,TouchButton.LEFT,totalDuration,steps);
    }

    public void swipe(int startX, int startY, int endX, int endY, TouchButton button, long totalDuration, int steps) {
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

    public void move(int x, int y) {
        touchPoint.move(x, y);
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
