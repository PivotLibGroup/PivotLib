package com.pivotgroup.pivotlib.actionsimulator.action;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;

/**
 * 双击动作
 */
public class DoubleTap implements Action {

    private final long duration;

    public DoubleTap() {
        this(50);
    }

    public DoubleTap(long duration) {
        this.duration = duration;
    }

    @Override
    public void exec(ActionSimulator actionSimulator, int x, int y) {
        actionSimulator.tap(x, y, duration);
        actionSimulator.sleep(duration);
        actionSimulator.tap(x, y, duration);
    }
}
