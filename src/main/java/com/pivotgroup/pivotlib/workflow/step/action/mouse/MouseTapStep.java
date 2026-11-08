package com.pivotgroup.pivotlib.workflow.step.action.mouse;

import com.pivotgroup.pivotlib.actionsimulator.MouseActionSimulator;
import com.pivotgroup.pivotlib.actionsimulator.touchpoint.MouseButton;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;
import com.pivotgroup.pivotlib.workflow.step.action.ActionStep;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 12:43
 * @description 鼠标点击
 */

public class MouseTapStep extends ActionStep {

    private final MouseButton mouseButton;
    private final long duration;

    public MouseTapStep(int x, int y, MouseButton mouseButton, long duration) {
        super(x, y);
        this.mouseButton = mouseButton;
        this.duration = duration;
    }

    @Override
    public void exec(WorkflowContext context) {
        MouseActionSimulator mas = (MouseActionSimulator) context.getActionSimulator();
        mas.tap(getStartX(), getStartY(), mouseButton, duration);
    }
}
