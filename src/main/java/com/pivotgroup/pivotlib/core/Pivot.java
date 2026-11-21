package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.infrastructure.action.Action;
import com.pivotgroup.pivotlib.infrastructure.action.ActionSequence;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class Pivot {

    private final ActionSequence actionSequence = new ActionSequence();

    public Pivot() {
    }

    public void startActions() {
        actionSequence.apply();
    }

    public void addAction(Action action) {
        this.actionSequence.addAction(action);
    }

}
