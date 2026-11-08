package com.pivotgroup.pivotlib.actionsimulator.action;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;

public class Swipe implements Action {

    private int startX;
    private int startY;
    private long totalDuration;
    private int steps;

    public Swipe(int startX, int startY) {
        this(startX, startY, 300, 10);
    }

    public Swipe(int startX, int startY, long totalDuration, int steps) {
        this.startX = startX;
        this.startY = startY;
        this.totalDuration = totalDuration;
        this.steps = steps;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    @Override
    public void exec(ActionSimulator actionSimulator, int x, int y) {
        actionSimulator.swipe(startX, startY, x, y, totalDuration, steps);
    }
}
