package com.pivotgroup.pivotlib.workflow;

import com.pivotgroup.pivotlib.ActionSimulator;

import java.util.ArrayList;
import java.util.List;

public class Workflow {

    private String name;

    private List<Step> steps = new ArrayList<>();

    public Workflow(String name) {
        this.name = name;
    }

    /**
     * 依次执行步骤
     * @param actionSimulator
     */
    public void exec(ActionSimulator actionSimulator) {
        for (Step step : steps) {
            step.exec(actionSimulator);
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
