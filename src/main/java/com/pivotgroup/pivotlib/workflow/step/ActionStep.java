package com.pivotgroup.pivotlib.workflow.step;

import com.pivotgroup.pivotlib.actionsimulator.Action;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 12:48
 * @description 动作步骤，接收所有动作
 */

public class ActionStep extends Step {

    private final Action action;

    public ActionStep(Action action) {
        this.action = action;
    }

    @Override
    public void exec(WorkflowContext context) {
        action.exec(context.getActionSimulator());
    }
}
