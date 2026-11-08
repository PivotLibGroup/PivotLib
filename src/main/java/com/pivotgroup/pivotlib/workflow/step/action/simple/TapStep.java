package com.pivotgroup.pivotlib.workflow.step.action.simple;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;
import com.pivotgroup.pivotlib.workflow.step.action.ActionStep;

public class TapStep extends ActionStep {

    private final long duration;

    /**
     * @param x        点击坐标x
     * @param y        点击坐标y
     * @param duration 按下时长(ms)
     */
    public TapStep(int x, int y, long duration) {
        super(x, y);
        this.duration = duration;
    }

    @Override
    public void exec(WorkflowContext context) {
        ActionSimulator actionSimulator = context.getActionSimulator();
        actionSimulator.tap(getStartX(), getStartY(), duration);
    }
}
