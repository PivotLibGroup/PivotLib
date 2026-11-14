package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.action.Operable;
import com.pivotgroup.pivotlib.action.OperationSimulator;
import com.pivotgroup.pivotlib.vision.Identifier;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/10 20:05
 */

public class Pivot {

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
    private List<Operable> operationList = new ArrayList<>();

    public Pivot(OperationSimulator os, Identifier identifier) {
        this.os = os;
        this.identifier = identifier;
    }

    public void start() {
        for (Operable operable : operationList) {
            boolean flag = false;
            for (Mat mat : identifiedList) {
                flag = identifier.identify(mat);
            }
            if (flag) {
                operable.operate(os);
            }
        }
    }

    public void addOperation(Operable operable) {
        operationList.add(operable);
    }

    public void addIdentified(Mat identifiedMat) {
        identifiedList.add(identifiedMat);
    }

}
