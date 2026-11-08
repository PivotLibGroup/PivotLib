package com.pivotgroup.pivotlib.workflow.step.action.simple;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.actionsimulator.action.Tap;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;
import com.pivotgroup.pivotlib.workflow.step.action.ActionStep;

public class TapStep extends ActionStep {

    private Tap tapAction;

    /**
     * @param x        点击坐标x
     * @param y        点击坐标y
     * @param duration 按下时长(ms)
     */
    public TapStep(int x, int y, long duration) {
        super(x, y);
        tapAction = new Tap(duration);
    }

    @Override
    public void exec(WorkflowContext context) {
        ActionSimulator actionSimulator = context.getActionSimulator();
        tapAction.exec(actionSimulator, getStartX(), getStartY());
    }
}
