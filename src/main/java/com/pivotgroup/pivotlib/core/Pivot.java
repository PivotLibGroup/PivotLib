package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.action.Operation;
import com.pivotgroup.pivotlib.action.OperationSimulator;
import com.pivotgroup.pivotlib.vision.Identifier;
import com.pivotgroup.pivotlib.vision.ImageCapturable;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 操作集
     */
    private List<Operation> operationList = new ArrayList<>();

    public Pivot(OperationSimulator os, ImageCapturable imageCapturable) {
        this.os = os;
        this.identifier = new Identifier(imageCapturable);
    }

    public void start() {
        for (Operation operation : operationList) {
            boolean flag = true;
            if (!identifiedList.isEmpty()) {
                flag = false;
                for (Mat mat : identifiedList) {
                    flag = identifier.identify(mat);
                    if (flag) {
                        break;
                    }
                }
            }
            if (flag) {
                operation.operate(os);
                log.info("\"{}\"操作执行完成", operation.getName());
            } else {
                log.info("识别失败, 并没有执行\"{}\"", operation.getName());
            }
        }
    }

    public void addOperation(Operation operable) {
        operationList.add(operable);
    }

    public void addIdentified(Mat identifiedMat) {
        identifiedList.add(identifiedMat);
    }

}
