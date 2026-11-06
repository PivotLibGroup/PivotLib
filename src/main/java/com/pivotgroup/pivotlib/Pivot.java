package com.pivotgroup.pivotlib;

import com.pivotgroup.pivotlib.workflow.Workflow;

import java.util.ArrayList;
import java.util.List;

/**
 * PivotLib 中枢：管理工作流并执行
 */
public class Pivot {

    private ActionSimulator actionSimulator;

    private List<Workflow> workflows = new ArrayList<>();

    public Pivot(ActionSimulator actionSimulator) {
        this.actionSimulator = actionSimulator;
    }

    public void addWorkflow(Workflow workflow) {
        workflows.add(workflow);
    }

    /**
     * 按名称查找并执行工作流
     * @return 0=成功，-1=未找到
     */
    public int executeWorkflow(String name) {
        for (Workflow wf : workflows) {
            if (wf.getName().equals(name)) {
                return executeWorkflow(wf);
            }
        }
        return -1;
    }

    /**
     * 直接执行指定工作流
     * @return 0=成功
     */
    public int executeWorkflow(Workflow workflow) {
        workflow.exec(actionSimulator);
        return 0;
    }

    public List<Workflow> getWorkflows() {
        return workflows;
    }
}
