package com.pivotgroup.pivotlib.workflow.step.action;

import com.pivotgroup.pivotlib.util.Anchor;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 12:48
 * @description
 */

public abstract class ActionStep extends Step {

    private int startX,startY;

    private Anchor anchor;

    public ActionStep(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public Anchor getAnchor() {
        return anchor;
    }

    public ActionStep setAnchor(Anchor anchor) {
        this.anchor = anchor;
        return this;
    }

}
