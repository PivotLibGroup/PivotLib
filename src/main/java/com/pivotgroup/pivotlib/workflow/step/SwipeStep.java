package com.pivotgroup.pivotlib.workflow.step;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;

public class SwipeStep extends Step {

    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private final long duration;
    private final int steps;

    /**
     * @param startX 起点x
     * @param startY 起点y
     * @param endX 终点x
     * @param endY 终点y
     * @param duration 滑动总时长(ms)，一般200~500ms
     * @param steps 移动步数，越多越平滑，一般20~50步
     */
    public SwipeStep(int startX, int startY, int endX, int endY, long duration, int steps) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.duration = duration;
        this.steps = steps;
    }

    @Override
    public void exec(WorkflowContext context) {
        ActionSimulator actionSimulator = context.getActionSimulator();
        actionSimulator.swipe(startX, startY, endX, endY, duration, steps);
    }
}
