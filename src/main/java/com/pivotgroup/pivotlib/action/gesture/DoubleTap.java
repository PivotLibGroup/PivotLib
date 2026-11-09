package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.simulator.ActionSimulator;
import com.pivotgroup.pivotlib.action.simulator.TouchButton;
import org.opencv.core.Mat;


public class DoubleTap extends Tap {

    private long duration;

    public DoubleTap(Mat canvas, Mat template, long duration) {
        super(canvas, template, 50);
        this.duration = duration;

    }

    public DoubleTap(int y, int x, long duration) {
        super(y, x, 50);
        this.duration = duration;

    }

    @Override
    public void exec(ActionSimulator actionSimulator) {
        super.exec(actionSimulator);
        actionSimulator.sleep(duration);
        super.exec(actionSimulator);
    }
}
