package com.pivotgroup.pivotlib.workflow.step.action;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.actionsimulator.action.Swipe;
import com.pivotgroup.pivotlib.util.Anchor;
import com.pivotgroup.pivotlib.util.Loc;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;
import org.opencv.core.Mat;

public class MatchSwipeStep extends Step {

    private int toX, toY;
    private double minScore;

    private Swipe swipe;

    private Mat tmpl;
    private Anchor anchor;

    public MatchSwipeStep(Mat template, int toX, int toY, Anchor anchor) {
        this(template, toX, toY, MatMatchUtils.DEFAULT_MIN_SCORE, anchor);
    }

    public MatchSwipeStep(Mat template, int toX, int toY, double minScore, Anchor anchor) {
        this.toX = toX;
        this.toY = toY;
        this.minScore = minScore;
        this.tmpl = template;
        this.anchor = anchor;
    }

    @Override
    public void exec(WorkflowContext context) {
        Mat canvas = context.canvasMat();
        Loc loc = MatMatchUtils.matchMultiScale(canvas, tmpl);
        if (loc.matchResult < minScore) {
            return;
        }
        ActionSimulator actionSimulator = context.getActionSimulator();
        Loc l = Anchor.anchorDelta(anchor, loc.x, loc.y, tmpl);
        swipe = new Swipe(l.x, l.y);
        swipe.exec(actionSimulator, toX, toY);
    }
}
