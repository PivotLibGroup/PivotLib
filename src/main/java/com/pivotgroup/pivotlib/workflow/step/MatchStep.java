package com.pivotgroup.pivotlib.workflow.step;

import com.pivotgroup.pivotlib.util.Loc;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.StepContext;
import org.opencv.core.Mat;

public class MatchStep extends Step {

    private Mat template;

    public MatchStep(Mat template) {
        this.template = template;
    }

    @Override
    public void exec(StepContext context) {
        Mat screenshot = context.screenshotMat();
        Loc loc = MatMatchUtils.match(screenshot, template);
        context.setData("matching_x", loc.x);
        context.setData("matching_y", loc.y);
    }
}
