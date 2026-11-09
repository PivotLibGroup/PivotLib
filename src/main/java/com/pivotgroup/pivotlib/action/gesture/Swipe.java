package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.CoordMode;
import com.pivotgroup.pivotlib.action.simulator.ActionSimulator;
import com.pivotgroup.pivotlib.util.coordinate.Loc;
import com.pivotgroup.pivotlib.util.MatUtils;
import org.opencv.core.Mat;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 22:04
 * @description
 */

public class Swipe extends Action {

    private Loc start;
    private Loc end;

    private long duration;
    private int steps;
    private CoordMode coordMode;

    /**
     * 识别位置向指定位置拖动
     * @param canvas 匹配的背景
     * @param template 识别样本
     * @param endX to x
     * @param endY to y
     * @param coordMode 坐标模式
     * @param duration 持续时长
     * @param steps 步数
     */
    public Swipe(Mat canvas, Mat template, int endX, int endY, CoordMode coordMode, long duration, int steps) {
        Loc rl = recognize(canvas, template);

        start = toCenter(rl, template);
        end = new Loc(endX,endY);

        this.coordMode = coordMode;
        this.duration = duration;
        this.steps = steps;
    }

    /**
     * 两个识别点进行位移，源样本位置移动到目标样本位置
     * @param canvas 匹配的背景
     * @param sourceTemplate 源识别样本
     * @param targetTemplate 目标识别样本
     * @param duration 持续时长
     * @param steps 步数
     */
    public Swipe(Mat canvas, Mat sourceTemplate, Mat targetTemplate, long duration, int steps) {
        this.duration = duration;
        this.steps = steps;
        this.coordMode = CoordMode.ABSOLUTE;

        Loc sl = recognize(canvas, sourceTemplate);
        Loc tl = recognize(canvas, targetTemplate);
        this.start = toCenter(sl, sourceTemplate);
        this.end = toCenter(tl, targetTemplate);
    }

    public Swipe(Loc l1, Loc l2, long duration, int steps) {
        this.start = l1;
        this.end = l2;
        this.steps = steps;
        this.duration = duration;
        this.coordMode = CoordMode.ABSOLUTE;
    }

    public Swipe(int startX, int startY, int endX, int endY, CoordMode coordMode, long duration, int steps) {
        this.start = new Loc(startX,startY);
        this.end =  new Loc(endX,endY);
        this.coordMode = coordMode;
        this.duration = duration;
        this.steps = steps;
    }

    @Override
    public void exec(ActionSimulator actionSimulator) {
        if (!isValid()) {
            return;
        }
        int actualEndX = end.x;
        int actualEndY = end.y;
        if (coordMode == CoordMode.RELATIVE) {
            actualEndX += start.x;
            actualEndY += start.y;
        }
        actionSimulator.swipe(start.x, start.y, actualEndX, actualEndY, duration, steps);
    }

}
