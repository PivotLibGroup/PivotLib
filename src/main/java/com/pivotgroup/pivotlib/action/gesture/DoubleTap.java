package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.action.simulator.ActionSimulator;
import com.pivotgroup.pivotlib.action.simulator.TouchButton;

/**
 * 双击动作
 */
public class DoubleTap implements Action {

    private int x, y;
    private long duration;

    public DoubleTap(int y, int x) {
        this(x, y, 50);
    }

    public DoubleTap(int y, int x, long duration) {
        this.y = y;
        this.x = x;
        this.duration = duration;
    }

    public DoubleTap(long duration) {
        this.duration = duration;
    }

    @Override
    public void exec(ActionSimulator actionSimulator) {
        actionSimulator.tap(x, y, TouchButton.LEFT, duration);
        actionSimulator.sleep(duration);
        actionSimulator.tap(x, y, TouchButton.LEFT, duration);
    }
}
