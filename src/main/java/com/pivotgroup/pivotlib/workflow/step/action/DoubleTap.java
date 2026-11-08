package com.pivotgroup.pivotlib.workflow.step.action;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;

/**
 * 双击动作
 */
public class DoubleTap implements Action {

    private final int duration;

    public DoubleTap() {
        this(50);
    }

    public DoubleTap(int duration) {
        this.duration = duration;
    }

    @Override
    public void exec(ActionSimulator actionSimulator, int x, int y) {
        actionSimulator.tap(x, y, duration);
        actionSimulator.sleep(duration);
        actionSimulator.tap(x, y, duration);
    }
}
