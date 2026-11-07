package com.pivotgroup.pivotlib.workflow.step;

import com.pivotgroup.pivotlib.ActionSimulator;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.StepContext;

public class OpMatchedStep extends Step {

    @Override
    public void exec(StepContext context) {
        int x = (int) context.getData("matching_x");
        int y = (int) context.getData("matching_y");
        System.out.println("匹配的坐标是：" + x + " " + y);
        ActionSimulator actionSimulator = context.getActionSimulator();
        actionSimulator.tap(x, y, 50);
    }

}
