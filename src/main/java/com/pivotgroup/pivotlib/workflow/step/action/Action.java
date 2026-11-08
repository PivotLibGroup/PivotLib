package com.pivotgroup.pivotlib.workflow.step.action;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;

/**
 * 手势动作接口：在指定坐标上执行一个动作
 */
public interface Action {

    void exec(ActionSimulator actionSimulator, int x, int y);
}
