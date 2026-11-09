package com.pivotgroup.pivotlib;

import com.pivotgroup.pivotlib.capture.ScreenCapturer;
import com.pivotgroup.pivotlib.input.Pointer;
import com.pivotgroup.pivotlib.workflow.Workflow;

import java.util.ArrayList;
import java.util.List;

public class Pivot {

    private final List<Workflow> workflows = new ArrayList<>();

    private final Pointer pointer;
    private final ScreenCapturer screenCapturer;

    public Pivot(Pointer pointer, ScreenCapturer screenCapturer) {
        this.pointer = pointer;
        this.screenCapturer = screenCapturer;
    }

    public Workflow createWorkflow(String name) {
        return new Workflow(name, pointer, screenCapturer);
    }

    public void addWorkflow(Workflow workflow) {
        workflows.add(workflow);
    }

    public boolean executeWorkflow(String name) {
        Workflow workflow = findWorkflow(name);
        if (workflow == null) {
            return false;
        }
        return workflow.exec();
    }

    public Workflow findWorkflow(String name) {
        for (Workflow workflow : workflows) {
            if (workflow.getName().equals(name)) {
                return workflow;
            }
        }
        return null;
    }

    public List<Workflow> getWorkflows() {
        return workflows;
    }
}
