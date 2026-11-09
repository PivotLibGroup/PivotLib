package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.action.CoordMode;
import com.pivotgroup.pivotlib.action.simulator.ActionSimulator;
import com.pivotgroup.pivotlib.util.Loc;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.util.MatUtils;
import org.opencv.core.Mat;

public class Swipe implements Action {

    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private long totalDuration;
    private int steps;
    private CoordMode coordMode;

    /**
     * 能否执行验证标志位
     */
    private boolean valid = true;

    public Swipe(Mat canvas, Mat template, int endX, int endY, CoordMode coordMode, long totalDuration, int steps) {
        Loc loc = MatMatchUtils.matchMultiScale(canvas, template);
        if (loc.matchResult < MatMatchUtils.DEFAULT_MIN_SCORE) {
            valid = false;
            return;
        }
        Loc centerLoc = MatUtils.toCenter(loc, template);
        this.startX = centerLoc.x;
        this.startY = centerLoc.y;
        this.endX = endX;
        this.endY = endY;
        this.coordMode = coordMode;
        this.totalDuration = totalDuration;
        this.steps = steps;
    }

    public Swipe(Mat canvas, Mat sourceTemplate, Mat targetTemplate, long totalDuration, int steps) {
        Loc sourceLoc = MatMatchUtils.matchMultiScale(canvas, sourceTemplate);
        Loc targetLoc = MatMatchUtils.matchMultiScale(canvas, targetTemplate);
        if (sourceLoc.matchResult < MatMatchUtils.DEFAULT_MIN_SCORE || targetLoc.matchResult < MatMatchUtils.DEFAULT_MIN_SCORE) {
            valid = false;
            return;
        }
        this.totalDuration = totalDuration;
        this.steps = steps;

        Loc sourceCenterLoc = MatUtils.toCenter(sourceLoc, sourceTemplate);
        Loc targetCenterLoc = MatUtils.toCenter(targetLoc, targetTemplate);
        this.startX = sourceCenterLoc.x;
        this.startY = sourceCenterLoc.y;
        this.endX = targetCenterLoc.x;
        this.endY = targetCenterLoc.y;
        this.coordMode = CoordMode.ABSOLUTE;
    }

    public Swipe(Loc l1, Loc l2, long totalDuration, int steps) {
        if (l1.matchResult < MatMatchUtils.DEFAULT_MIN_SCORE || l2.matchResult < MatMatchUtils.DEFAULT_MIN_SCORE) {
            valid = false;
            return;
        }

        this.startX = l1.x;
        this.startY = l1.y;
        this.endX = l2.x;
        this.endY = l2.y;
        this.totalDuration = totalDuration;
        this.steps = steps;
        this.coordMode = CoordMode.ABSOLUTE;
    }

    public Swipe(int startX, int startY, int endX, int endY, CoordMode coordMode, long totalDuration, int steps) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.coordMode = coordMode;
        this.totalDuration = totalDuration;
        this.steps = steps;
    }

    @Override
    public void exec(ActionSimulator actionSimulator) {
        if (!valid) {
            return;
        }
        if (coordMode == CoordMode.RELATIVE) {
            endX += startX;
            endY += startY;
        }
        actionSimulator.swipe(startX, startY, endX, endY, totalDuration, steps);
    }
}
