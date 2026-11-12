package com.pivotgroup.pivotlib_old.workflow;

import com.pivotgroup.pivotlib_old.capture.ScreenCapturer;
import com.pivotgroup.pivotlib_old.input.Pointer;

import java.util.HashMap;
import java.util.Map;

public class WorkflowContext {

    private boolean stopping;
    private boolean failed;
    private String failureMessage;

    private final Map<String, Object> dataMap = new HashMap<>();

    private Pointer pointer;
    private ScreenCapturer screenCapturer;

    public void reset() {
        stopping = false;
        failed = false;
        failureMessage = null;
        dataMap.clear();
        pointer = null;
        screenCapturer = null;
    }

    public void setData(String key, Object data) {
        this.dataMap.put(key, data);
    }

    public Object getData(String key) {
        return this.dataMap.get(key);
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setScreenCapture(ScreenCapturer screenCapturer) {
        this.screenCapturer = screenCapturer;
    }

    public ScreenCapturer getScreenCapturer() {
        return screenCapturer;
    }

    public void setPointer(Pointer pointer) {
        this.pointer = pointer;
    }

    public Pointer pointer() {
        return pointer;
    }

    public void stop() {
        stopping = true;
    }

    public boolean isStopping() {
        return stopping;
    }

    public void fail(String reason) {
        if (!failed) {
            failed = true;
            failureMessage = reason;
        }
    }

    public boolean isFailed() {
        return failed;
    }

    public String failureMessage() {
        return failureMessage;
    }
}
