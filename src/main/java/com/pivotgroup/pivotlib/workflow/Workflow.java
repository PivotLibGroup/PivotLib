package com.pivotgroup.pivotlib.workflow;

import com.pivotgroup.pivotlib.ActionSimulator;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作流：按顺序执行一组步骤
 */
public class Workflow {

    private String name;

    private int index;

    private List<Step> steps = new ArrayList<>();

    public Workflow(String name) {
        this.name = name;
    }

    /**
     * 按顺序执行所有步骤
     */
    public void exec(ActionSimulator actionSimulator) {
        for (Step step : steps) {
            step.exec(actionSimulator);
        }
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public String getName() {
        return name;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
