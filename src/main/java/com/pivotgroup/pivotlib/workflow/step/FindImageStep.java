package com.pivotgroup.pivotlib.workflow.step;

import com.pivotgroup.pivotlib.util.*;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.WorkflowContext;
import org.opencv.core.Mat;

public class FindImageStep extends Step {

    public static final String RESULT_KEY = "match";

    private Mat template;

    public FindImageStep(Mat template) {
        this.template = template;
    }

    public FindImageStep(String templatePath) {
        this.template = MatUtils.loadImage(templatePath);
    }

    @Override
    public void exec(WorkflowContext context) {
        Mat screenshot = context.canvasMat();
        try {
            MaxLocResult loc = MatMatchUtils.matchMultiScale(screenshot, template);
            context.setData(RESULT_KEY, new MatchInfo(screenshot, template, loc.maxLoc));
        } finally {
            screenshot.release();
        }
    }
}
