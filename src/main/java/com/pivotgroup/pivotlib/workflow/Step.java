package com.pivotgroup.pivotlib.workflow;

import com.pivotgroup.pivotlib.ActionSimulator;

public abstract class Step {

    /**
     * 执行本步骤
     * @param simulator 底层动作模拟器
     */
    public abstract void exec(ActionSimulator simulator);
}
