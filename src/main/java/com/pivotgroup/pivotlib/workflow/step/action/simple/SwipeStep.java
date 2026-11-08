package com.pivotgroup.pivotlib.workflow.step.action.simple;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.actionsimulator.action.Swipe;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;
import com.pivotgroup.pivotlib.workflow.step.action.ActionStep;

public class SwipeStep extends ActionStep {

    private Swipe swipe;

    public SwipeStep(int startX, int startY, int dx, int dy, long totalDuration, int steps) {
        super(startX, startY);
        swipe = new Swipe(dx, dy, totalDuration, steps);
    }

    @Override
    public void exec(WorkflowContext context) {
        ActionSimulator asm = context.getActionSimulator();
        swipe.exec(asm, getStartX(), getStartY());
    }

}
