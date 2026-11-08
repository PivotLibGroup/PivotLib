package com.pivotgroup.pivotlib.workflow.step.action;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;

/**
 * 相对滑动动作：从(x,y)偏移(dx, dy)
 */
public class Swipe implements Action {

    private final int dx;
    private final int dy;
    private final long totalDuration;
    private final int steps;

    public Swipe(int dx, int dy) {
        this(dx, dy, 300, 10);
    }

    public Swipe(int dx, int dy, long totalDuration, int steps) {
        this.dx = dx;
        this.dy = dy;
        this.totalDuration = totalDuration;
        this.steps = steps;
    }

    @Override
    public void exec(ActionSimulator actionSimulator, int x, int y) {
        actionSimulator.swipe(x, y, x + dx, y + dy, totalDuration, steps);
    }
}
