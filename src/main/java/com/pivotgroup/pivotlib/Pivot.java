package com.pivotgroup.pivotlib;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.workflow.Workflow;

import java.util.ArrayList;
import java.util.List;

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
        Workflow wf = findWorkflow(name);
        if (wf == null) {
            return -1;
        }
        return executeWorkflow(wf);
    }

    /**
     * 直接执行指定工作流
     * @return 0=成功
     */
    public int executeWorkflow(Workflow workflow) {
        workflow.exec(actionSimulator);
        return 0;
    }

    public Workflow findWorkflow(String name) {
        for (Workflow wf : workflows) {
            if (wf.getName().equals(name)) {
                return wf;
            }
        }
        return null;
    }

    public List<Workflow> getWorkflows() {
        return workflows;
    }
}
