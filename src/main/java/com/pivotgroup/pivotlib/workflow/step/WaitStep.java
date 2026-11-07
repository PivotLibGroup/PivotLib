package com.pivotgroup.pivotlib.workflow.step;

import com.pivotgroup.pivotlib.ActionSimulator;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.StepContext;

public class WaitStep extends Step {

    private final long ms;

    /**
     * @param ms 等待时长(ms)
     */
    public WaitStep(long ms) {
        this.ms = ms;
    }

    @Override
    public void exec(StepContext context) {
        ActionSimulator actionSimulator = context.getActionSimulator();
        actionSimulator.sleep(ms);
    }
}
