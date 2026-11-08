package com.pivotgroup.pivotlib.workflow;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.screencapture.ScreenCapturer;
import org.opencv.core.Mat;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WorkflowContext {

    private static final Robot robot;

    private boolean stoping = false;

    private final Map<String, Object> dataMap = new HashMap<>();

    /**
     * 动作模拟器
     */
    private ActionSimulator actionSimulator;

    // TODO 后续在这里放图像工具
    // TODO 还需要像ActionSimulator一样，从调用者那里获得图像
    // 然后把那个对象塞进context
    private ScreenCapturer screenCapturer;

    static {
        try {
            robot = new Robot();

        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public WorkflowContext() {

    }

    public Mat screenshotMat() {
        return screenCapturer.getScreen();
    }

    public void close() {
        stoping = false;
        actionSimulator = null;
        screenCapturer = null;
        dataMap.clear();
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

    public void setActionSimulator(ActionSimulator actionSimulator) {
        this.actionSimulator = actionSimulator;
    }

    public ActionSimulator getActionSimulator() {
        return actionSimulator;
    }

    public void stop() {
        stoping = true;
    }

    public boolean isStoping() {
        return stoping;
    }


}
