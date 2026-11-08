package com.pivotgroup.pivotlib.workflow.step.action;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.actionsimulator.action.DoubleTap;
import com.pivotgroup.pivotlib.actionsimulator.action.Tap;
import com.pivotgroup.pivotlib.util.Loc;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.util.TapType;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;
import org.opencv.core.Mat;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 17:39
 * @description
 */

public class MatchTapStep extends ActionStep {

    private long duration;
    private Mat tmpl;
    private TapType type;

    public MatchTapStep(Mat template, TapType type, long duration) {
        super(0, 0);
        this.tmpl = template;
        this.type = type;
        this.duration = duration;
    }

    @Override
    public void exec(WorkflowContext context) {
        Mat canvas = context.canvasMat();
        Loc loc = MatMatchUtils.matchMultiScale(canvas, tmpl);
        if (loc.matchResult < MatMatchUtils.DEFAULT_MIN_SCORE) {
            return;
        }
        ActionSimulator as = context.getActionSimulator();
        switch (type) {
            case TAP_TYPE -> new Tap(duration).exec(as, loc.x, loc.y);
            case DOUBLE_TAP_TYPE -> new DoubleTap(duration).exec(as, loc.x, loc.y);
        }
    }
}
