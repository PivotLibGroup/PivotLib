package com.pivotgroup.pivotlib.workflow;

import com.pivotgroup.pivotlib.ActionSimulator;

public abstract class Step {

    /**
     * 执行本步骤
     * @param context 步骤上下文
     */
    public abstract void exec(StepContext context);
}
