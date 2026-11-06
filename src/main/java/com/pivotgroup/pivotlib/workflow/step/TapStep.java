package com.pivotgroup.pivotlib.workflow.step;

import com.pivotgroup.pivotlib.ActionSimulator;
import com.pivotgroup.pivotlib.workflow.Step;

public class TapStep extends Step {

    private final int x;
    private final int y;
    private final long duration;

    /**
     * @param x 点击坐标x
     * @param y 点击坐标y
     * @param duration 按下时长(ms)
     */
    public TapStep(int x, int y, long duration) {
        this.x = x;
        this.y = y;
        this.duration = duration;
    }

    @Override
    public void exec(ActionSimulator simulator) {
        simulator.tap(x, y, duration);
    }
}
