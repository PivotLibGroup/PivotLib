package com.pivotgroup.pivotlib.workflow;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.actionsimulator.SimpleActionSimulator;
import com.pivotgroup.pivotlib.screencapture.ScreenCapturer;

import java.util.ArrayList;
import java.util.List;

public class Workflow {

    private String name;

    private List<Step> steps = new ArrayList<>();

    private WorkflowContext workflowContext = new WorkflowContext();

    public Workflow(String name) {
        this.name = name;
    }

    /**
     * 依次执行步骤
     *
     * @param actionSimulator
     * @param screenCapturer
     */
    public void exec(ActionSimulator actionSimulator, ScreenCapturer screenCapturer) {
        // 构建上下文
        workflowContext.close();
        workflowContext.setActionSimulator(actionSimulator);
        workflowContext.setScreenCapture(screenCapturer);
        for (Step step : steps) {
            if (!workflowContext.isStoping()) {
                step.exec(workflowContext);
            }
        }
    }

    /**
     * 添加步骤
     * @param step
     */
    public void addStep(Step step) {
        steps.add(step);
    }

    /**
     * 获取名字
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 获得所有步骤
     * @return 步骤集
     */
    public List<Step> getSteps() {
        return steps;
    }
}
