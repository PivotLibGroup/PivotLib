package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.action.Operation;
import com.pivotgroup.pivotlib.action.OperationSimulator;
import com.pivotgroup.pivotlib.action.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.ResultLoc;
import com.pivotgroup.pivotlib.vision.Identifier;
import com.pivotgroup.pivotlib.vision.ImageCapturable;
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

    private static final Logger log = LoggerFactory.getLogger(Pivot.class);

    /**
     * 操作模拟器
     */
    private OperationSimulator os;

    /**
     * 画面识别器
     */
    private Identifier identifier;

    /**
     * 识别图集
     */
    private List<Mat> identifiedList = new ArrayList<>();
    private Map<String, Mat> identifiedMap = new HashMap<>();

    /**
     * 操作集
     */
    private List<Operation> operationList = new ArrayList<>();

    public Pivot(TouchCapturable tc, ImageCapturable imageCapturable) {
        this.os = new OperationSimulator(tc);
        this.identifier = new Identifier(imageCapturable);

    }

    public void start() {
        for (Operation operation : operationList) {
            // 识别画面是否存在样本图, 如果不存在不予执行操作
            boolean flag = true;
            if (!identifiedList.isEmpty()) {
                flag = false;
                for (Mat mat : identifiedList) {
                    flag = identifier.isPresent(mat);
                    if (flag) {
                        break;
                    }
                }
            }
            if (flag) {
                // 执行操作
                long startTime = System.currentTimeMillis();
                operation.operate(os);
                long endTime = System.currentTimeMillis();
                log.info("\"{}\"操作执行完成,耗时:{}ms", operation.getName(), (endTime - startTime));
            } else {
                log.info("识别失败, 并没有执行\"{}\"", operation.getName());
            }
        }
    }

    public void addOperation(Operation operable) {
        operationList.add(operable);
    }

    public void putIdentified(String name, Mat identifiedMat) {
        identifiedMap.put(name, identifiedMat);
    }

    public void addIdentified(Mat identifiedMat) {
        identifiedList.add(identifiedMat);
    }

    public Location locate(Mat templ) {
        ResultLoc identify = identifier.identify(templ);
        if (identify.isPresent()) {
            return identify.loc;
        }
        throw new NullPointerException("\"" + templ.toString() + "\"" +
                "This Mat does not exist on the canvas.");
    }

    public ResultLoc identify(Mat templ) {
        return identifier.identify(templ);
    }

}
