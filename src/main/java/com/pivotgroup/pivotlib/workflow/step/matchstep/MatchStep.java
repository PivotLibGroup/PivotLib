package com.pivotgroup.pivotlib.workflow.step.matchstep;

import com.pivotgroup.pivotlib.util.Loc;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.util.MatUtils;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.StepContext;
import org.opencv.core.Mat;

public class MatchStep extends Step {

    public static final String RESULT_KEY = "match";

    private Mat template;

    public MatchStep(Mat template) {
        this.template = template;
    }

    public MatchStep(String templatePath) {
        this.template = MatUtils.loadImage(templatePath);
    }

    @Override
    public void exec(StepContext context) {
        Mat screenshot = context.screenshotMat();
        try {
            Loc loc = MatMatchUtils.matchMultiScale(screenshot, template);
            context.setData(RESULT_KEY, new MatchInfo(screenshot, template, loc));
        } finally {
            screenshot.release();
        }
    }
}
