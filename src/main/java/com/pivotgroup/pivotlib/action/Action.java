package com.pivotgroup.pivotlib.action;

import com.pivotgroup.pivotlib.action.simulator.ActionSimulator;

public interface Action {

    void exec(ActionSimulator actionSimulator);
}
