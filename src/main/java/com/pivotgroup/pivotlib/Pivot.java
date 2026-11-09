package com.pivotgroup.pivotlib;

import com.pivotgroup.pivotlib.action.simulator.ActionSimulator;
import com.pivotgroup.pivotlib.screencapture.ScreenCapturer;
import com.pivotgroup.pivotlib.workflow.Workflow;

import java.util.ArrayList;
import java.util.List;

public class Pivot {

    private List<Workflow> workflows = new ArrayList<>();

    private ActionSimulator actionSimulator;

    private ScreenCapturer screenCapturer;

    public Pivot(ActionSimulator actionSimulator, ScreenCapturer screenCapturer) {
        this.actionSimulator = actionSimulator;
        this.screenCapturer = screenCapturer;

    }

    public void addWorkflow(Workflow workflow) {
        workflows.add(workflow);
    }

    /**
     * 按名称查找并执行工作流
     *
     * @return 0=成功，-1=未找到
     */
    public int executeWorkflow(String name) {
        Workflow wf = findWorkflow(name);
        if (wf == null) {
            return -1;
        }
        wf.exec(actionSimulator, screenCapturer);
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
