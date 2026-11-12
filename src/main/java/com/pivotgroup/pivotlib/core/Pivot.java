package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.action.OperationSimulator;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/10 20:05
 */

public class Pivot {

    private OperationSimulator os;

    public Pivot(OperationSimulator os) {
        this.os = os;
    }

    public void start() {
        os.move(100, 100, 2000, 100);
    }

}
