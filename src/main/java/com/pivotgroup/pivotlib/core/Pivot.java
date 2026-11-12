package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.action.Operable;
import com.pivotgroup.pivotlib.action.OperationSimulator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/10 20:05
 */

public class Pivot {

    private OperationSimulator os;

    private List<Operable> operables = new ArrayList<>();

    public Pivot(OperationSimulator os) {
        this.os = os;
    }

    public void start() {
        for (Operable operable : operables) {
            operable.operate(os);
        }
    }

    public void addOperation(Operable operable) {
        operables.add(operable);
    }

}
