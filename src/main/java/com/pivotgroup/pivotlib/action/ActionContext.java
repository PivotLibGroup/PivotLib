package com.pivotgroup.pivotlib.action;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class ActionContext {

    private boolean stopping = false;

    public boolean isStopping() {
        return stopping;
    }

    public void stop() {
        this.stopping = true;
    }
}
