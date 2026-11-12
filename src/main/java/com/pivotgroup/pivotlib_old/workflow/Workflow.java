package com.pivotgroup.pivotlib_old.workflow;

import com.pivotgroup.pivotlib_old.capture.ScreenCapturer;
import com.pivotgroup.pivotlib_old.input.Pointer;

import java.util.ArrayList;
import java.util.List;

public class Workflow {

    private final String name;
    private final Pointer pointer;
    private final ScreenCapturer screenCapturer;

    private final List<Step> steps = new ArrayList<>();
    private final WorkflowContext context = new WorkflowContext();

    public Workflow(String name, Pointer pointer, ScreenCapturer screenCapturer) {
        this.name = name;
        this.pointer = pointer;
        this.screenCapturer = screenCapturer;
    }

    public boolean exec() {
        System.out.println("[execute] name: " + name);
        context.reset();
        context.setPointer(pointer);
        context.setScreenCapture(screenCapturer);

        for (Step step : steps) {
            if (context.isStopping()) {
                break;
            }
            step.exec(context);
        }
        return !context.isFailed();
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

    public WorkflowContext getContext() {
        return context;
    }
}
