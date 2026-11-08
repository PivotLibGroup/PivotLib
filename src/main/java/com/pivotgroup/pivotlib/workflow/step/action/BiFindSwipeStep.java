package com.pivotgroup.pivotlib.workflow.step.action;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.actionsimulator.action.Swipe;
import com.pivotgroup.pivotlib.util.Anchor;
import com.pivotgroup.pivotlib.util.Loc;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;
import org.opencv.core.Mat;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 14:53
 * @description
 */

public class BiFindSwipeStep extends ActionStep {

    private Swipe swipe;

    private Mat tmplSource;
    private Mat tmplTarget;

    private double minScore = MatMatchUtils.DEFAULT_MIN_SCORE;

    /**
     * 将源移动到目标
     *
     * @param tmplSource 第一个源模板
     * @param tmplTarget 第二个目标模板
     */
    public BiFindSwipeStep(Mat tmplSource, Mat tmplTarget) {
        super(0, 0);

        this.tmplSource = tmplSource;
        this.tmplTarget = tmplTarget;
    }

    @Override
    public void exec(WorkflowContext context) {
        Mat canvas = context.canvasMat();
        Loc sourceLoc = MatMatchUtils.matchMultiScale(canvas, tmplSource);
        Loc targetLoc = MatMatchUtils.matchMultiScale(canvas, tmplTarget);
        if (sourceLoc.matchResult < minScore || targetLoc.matchResult < minScore) {
            context.stop();
            return;
        }
        // 有了源位置和目标位置
        swipe = new Swipe(targetLoc.x, targetLoc.y, 2000, 100);
        ActionSimulator as = context.getActionSimulator();
        Loc ads = Anchor.anchorDelta(Anchor.CENTER, sourceLoc.x, sourceLoc.y, tmplSource);
        Loc adt = Anchor.anchorDelta(Anchor.CENTER, targetLoc.x, targetLoc.y, tmplTarget);
        swipe = new Swipe(ads.x, ads.y, 500, 50);
        swipe.exec(as, adt.x, adt.y);
    }

}
