package com.pivotgroup.pivotlib.workflow;

import com.pivotgroup.pivotlib.ActionSimulator;
import com.pivotgroup.pivotlib.util.MatUtils;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StepContext {

    private static final Robot robot;

    private boolean stoping = false;

    private final Map<String, Object> dataMap = new HashMap<>();

    /**
     * 动作模拟器
     */
    private ActionSimulator actionSimulator;

    // TODO 后续在这里放图像工具

    static {
        try {
            robot = new Robot();


        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public StepContext() {

    }

    public void close() {
        stoping = false;
        actionSimulator = null;
        dataMap.clear();

    }

    public Mat screenshotMat() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        BufferedImage img = robot.createScreenCapture(new Rectangle(d));
        File file = new File("screenshot.png");
        try {
            ImageIO.write(img, "png", file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return MatUtils.bufferedImageToMat(img);
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
