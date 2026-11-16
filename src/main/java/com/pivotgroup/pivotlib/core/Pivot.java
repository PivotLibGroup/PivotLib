package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.action.Operation;
import com.pivotgroup.pivotlib.action.simulator.DesktopOperationSimulator;
import com.pivotgroup.pivotlib.device.ImageCapturable;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;
import com.pivotgroup.pivotlib.model.ResultLoc;
import com.pivotgroup.pivotlib.vision.Identifier;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/10 20:05
 */

public class Pivot {

    /**
     * log
     */
    private static final Logger log = LoggerFactory.getLogger(Pivot.class);

    /**
     * Region of interest
     */
    private Rect roi;

    /**
     * 操作模拟器
     */
    private DesktopOperationSimulator os;

    /**
     * 画面识别器
     */
    private Identifier identifier;

    /**
     * 识别图集
     */
    private Map<String, Mat> identifiedMap = new HashMap<>();

    /**
     * 操作集
     */
    private List<Operation> operationList = new ArrayList<>();

    /**
     * 顶级容器
     */
    private Workspace workspace;

    /**
     *
     * @param tc
     * @param image
     */
    public Pivot(TouchCapturable tc, ImageCapturable image) {
        this.os = new DesktopOperationSimulator(tc);
        this.identifier = new Identifier(image);
    }

    /**
     * 执行操作
     */
    public void start() {
        for (Operation operation : operationList) {
            // 执行操作
            long startTime = System.currentTimeMillis();
            operation.operate(os);
            long endTime = System.currentTimeMillis();
            log.info("\"{}\"操作执行完成,耗时:{}ms", operation.getName(), (endTime - startTime));
        }
    }

    /**
     *
     * @param operable
     */
    public void addOperation(Operation operable) {
        operationList.add(operable);
    }

    /**
     * 添加识别对象
     *
     * @param name
     * @param identifiedMat
     */
    public void putIdentified(String name, Mat identifiedMat) {
        identifiedMap.put(name, identifiedMat);
    }

    /**
     * 识别图像位置
     *
     * @param templ
     * @return
     */
    public Location locate(Mat templ) {
        ResultLoc identify = identifier.identify(templ);
        if (identify.isPresent()) {
            return identify.loc;
        }
        throw new NullPointerException("\"" + templ.toString() + "\"" +
                "This Mat does not exist on the canvas.");
    }

}
