package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.action.simulator.ActionSimulator;

/**
 * 点击动作
 */
public class Tap implements Action {

    private int x, y;
    private long duration;

    public Tap(int x, int y) {
        this(x, y, 50);
    }

    public Tap(int y, int x, long duration) {
        this.y = y;
        this.x = x;
        this.duration = duration;
    }

    @Override
    public void exec(ActionSimulator actionSimulator) {
        actionSimulator.tap(x, y, duration);
    }
}
