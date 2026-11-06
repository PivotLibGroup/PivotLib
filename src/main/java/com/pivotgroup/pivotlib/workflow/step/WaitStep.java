package com.pivotgroup.pivotlib.workflow.step;

import com.pivotgroup.pivotlib.ActionSimulator;
import com.pivotgroup.pivotlib.workflow.Step;

public class WaitStep extends Step {

    private final long ms;

    /**
     * @param ms 等待时长(ms)
     */
    public WaitStep(long ms) {
        this.ms = ms;
    }

    @Override
    public void exec(ActionSimulator simulator) {
        simulator.sleep(ms);
    }
}
