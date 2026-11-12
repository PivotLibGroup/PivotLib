package com.pivotgroup.pivotlib.action.operation;

import com.pivotgroup.pivotlib.action.Operable;
import com.pivotgroup.pivotlib.action.OperationSimulator;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 18:56
 */

public class ClickOperation implements Operable {

    private int button;

    private long durationMs;

    public ClickOperation(int button, long durationMs) {
        this.button = button;
        this.durationMs = durationMs;
    }

    @Override
    public void operate(OperationSimulator operationSimulator) {
        operationSimulator.click(button, durationMs);
    }
}
