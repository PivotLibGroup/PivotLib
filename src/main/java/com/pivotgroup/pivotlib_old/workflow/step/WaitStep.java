package com.pivotgroup.pivotlib_old.workflow.step;

import com.pivotgroup.pivotlib_old.workflow.Step;
import com.pivotgroup.pivotlib_old.workflow.WorkflowContext;

public class WaitStep extends Step {

    private final long ms;

    public WaitStep(long ms) {
        this.ms = ms;
    }

    @Override
    public void exec(WorkflowContext context) {
        context.pointer().sleep(ms);
    }
}
