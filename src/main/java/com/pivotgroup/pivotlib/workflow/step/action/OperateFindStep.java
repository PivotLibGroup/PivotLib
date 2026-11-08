package com.pivotgroup.pivotlib.workflow.step.action;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.util.Anchor;
import com.pivotgroup.pivotlib.util.Loc;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.util.MatchInfo;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;
import com.pivotgroup.pivotlib.workflow.step.FindImageStep;
import org.opencv.core.Mat;

/**
 * 找到图后执行动作：从 FindImageStep 的结果中取坐标，做锚点转换后执行指定动作
 */
public class OperateFindStep extends Step {

    private final double minScore;
    private final Action action;
    private Anchor anchor;

    public OperateFindStep(Action action, Anchor anchor) {
        this(MatMatchUtils.DEFAULT_MIN_SCORE, action, anchor);
    }

    public OperateFindStep(double minScore, Action action, Anchor anchor) {
        this.minScore = minScore;
        this.action = action;
        this.anchor = anchor;
    }

    @Override
    public void exec(WorkflowContext context) {
        Object data = context.getData(FindImageStep.RESULT_KEY);
        if (!(data instanceof MatchInfo info)) {
            return;
        }
        Loc loc = info.loc;
        if (loc.matchResult < minScore) {
            return;
        }
        ActionSimulator actionSimulator = context.getActionSimulator();
        if (actionSimulator == null) {
            return;
        }

        // 根据锚点计算实际操作坐标
        int tx;
        int ty;

        Mat template = info.template;

        switch (anchor) {
            case TOP_LEFT:
                tx = loc.x;
                ty = loc.y;
                break;
            case TOP_CENTER:
                tx = loc.x + template.cols() / 2;
                ty = loc.y;
                break;
            case TOP_RIGHT:
                tx = loc.x + template.cols();
                ty = loc.y;
                break;
            case MIDDLE_LEFT:
                tx = loc.x;
                ty = loc.y + template.rows() / 2;
                break;
            case CENTER:
                tx = loc.x + template.cols() / 2;
                ty = loc.y + template.rows() / 2;
                break;
            case MIDDLE_RIGHT:
                tx = loc.x + template.cols();
                ty = loc.y + template.rows() / 2;
                break;
            case BOTTOM_LEFT:
                tx = loc.x;
                ty = loc.y + template.rows();
                break;
            case BOTTOM_CENTER:
                tx = loc.x + template.cols() / 2;
                ty = loc.y + template.rows();
                break;
            case BOTTOM_RIGHT:
                tx = loc.x + template.cols();
                ty = loc.y + template.rows();
                break;
            default:
                tx = loc.x;
                ty = loc.y;
        }

        action.exec(actionSimulator, tx, ty);
    }
}
