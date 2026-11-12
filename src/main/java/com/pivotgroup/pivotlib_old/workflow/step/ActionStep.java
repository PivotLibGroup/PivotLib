package com.pivotgroup.pivotlib_old.workflow.step;

import com.pivotgroup.pivotlib_old.input.Action;
import com.pivotgroup.pivotlib_old.input.ActionResult;
import com.pivotgroup.pivotlib_old.workflow.Step;
import com.pivotgroup.pivotlib_old.workflow.WorkflowContext;

public class ActionStep extends Step {

    public enum OnFailure { STOP, CONTINUE }

    private final Action action;
    private final OnFailure onFailure;

    public ActionStep(Action action) {
        this(action, OnFailure.STOP);
    }

    public ActionStep(Action action, OnFailure onFailure) {
        this.action = action;
        this.onFailure = onFailure;
    }

    @Override
    public void exec(WorkflowContext context) {
        ActionResult result = action.exec(context.pointer());
        if (!result.isOk()) {
            System.out.println("[FAIL] " + result.message());
            context.fail(result.message());
            if (onFailure == OnFailure.STOP) {
                context.stop();
            }
        }
    }
}
