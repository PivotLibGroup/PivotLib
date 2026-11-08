package com.pivotgroup.pivotlib.workflow.step.action;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;

/**
 * 点击动作
 */
public class Tap implements Action {

    private final int duration;

    public Tap() {
        this(50);
    }

    public Tap(int duration) {
        this.duration = duration;
    }

    @Override
    public void exec(ActionSimulator actionSimulator, int x, int y) {
        actionSimulator.tap(x, y, duration);
    }
}
