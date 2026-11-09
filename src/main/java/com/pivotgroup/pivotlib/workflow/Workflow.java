package com.pivotgroup.pivotlib.workflow;

import com.pivotgroup.pivotlib.action.simulator.ActionSimulator;
import com.pivotgroup.pivotlib.screencapture.ScreenCapturer;

import java.util.ArrayList;
import java.util.List;

public class Workflow {

    private String name;
    private final ActionSimulator mas;
    private final ScreenCapturer sc;

    private List<Step> steps = new ArrayList<>();

    private WorkflowContext workflowContext = new WorkflowContext();

    public Workflow(String name, ActionSimulator mas, ScreenCapturer sc) {
        this.name = name;
        this.mas = mas;
        this.sc = sc;

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
