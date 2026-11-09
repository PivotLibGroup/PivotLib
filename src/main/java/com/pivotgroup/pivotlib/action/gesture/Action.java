package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.simulator.ActionSimulator;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.util.MatUtils;
import com.pivotgroup.pivotlib.util.MaxLocResult;
import com.pivotgroup.pivotlib.util.coordinate.Loc;
import org.opencv.core.Mat;

public abstract class Action {
    /**
     * 能否执行验证标志位
     */
    private boolean valid = true;


    public abstract void exec(ActionSimulator actionSimulator);

    protected Loc recognize(Mat canvas, Mat template) {
        MaxLocResult maxLocResult = MatMatchUtils.matchMultiScale(canvas, template);
        if (maxLocResult.maxVal < MatMatchUtils.DEFAULT_MIN_SCORE) {
            valid = false;
        }
        return maxLocResult.maxLoc;
    }

    protected Loc toCenter(int x, int y, Mat image) {
        return MatUtils.toCenter(new Loc(x, y), image);
    }

    protected Loc toCenter(Loc l, Mat image) {
        return MatUtils.toCenter(l, image);
    }

    public boolean isValid() {
        return valid;
    }
}
