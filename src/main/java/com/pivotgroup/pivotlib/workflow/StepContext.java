package com.pivotgroup.pivotlib.workflow;

import com.pivotgroup.pivotlib.ActionSimulator;

public class StepContext {

    private boolean stoping = false;

    /**
     * 动作模拟器
     */
    private ActionSimulator actionSimulator;

    // TODO 后续在这里放图像工具

    public void close() {
        actionSimulator = null;

    }

    public void setActionSimulator(ActionSimulator actionSimulator) {
        this.actionSimulator = actionSimulator;
    }

    public ActionSimulator getActionSimulator() {
        return actionSimulator;
    }

    public void stop(){
        stoping = true;
    }

    public boolean isStoping(){
        return stoping;
    }

}
