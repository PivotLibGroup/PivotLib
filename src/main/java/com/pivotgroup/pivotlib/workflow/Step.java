package com.pivotgroup.pivotlib.workflow;

public abstract class Step {

    /**
     * 执行本步骤
     * @param context 步骤上下文
     */
    public abstract void exec(WorkflowContext context);
}
