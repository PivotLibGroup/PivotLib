package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.action.ActionSequence;

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
        ActionContext actionContext = new ActionContext();
        actionSequence.apply(actionContext);
    }

    public void addActionWithLocation(String name) {

    }

    public void addAction(Action action) {
        this.actionSequence.addAction(action);
    }

}
