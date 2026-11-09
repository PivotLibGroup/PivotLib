package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.simulator.ActionSimulator;
import com.pivotgroup.pivotlib.util.coordinate.Loc;
import org.opencv.core.Mat;

/**
 * 点击动作
 */
public class Tap extends Action {

    private int x, y;
    private long duration;

    public Tap(Mat canvas, Mat template, long duration) {
        Loc rl = recognize(canvas, template);
        this.x = rl.x + template.cols() / 2;
        this.y = rl.y + template.rows() / 2;
        this.duration = duration;
    }

    public Tap(int y, int x, long duration) {
        this.y = y;
        this.x = x;
        this.duration = duration;
    }

    @Override
    public void exec(ActionSimulator actionSimulator) {
        if (!isValid()) return;

        actionSimulator.tap(x, y, duration);
    }
}
