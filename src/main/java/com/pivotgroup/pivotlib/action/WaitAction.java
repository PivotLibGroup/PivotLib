package com.pivotgroup.pivotlib.action;

import java.util.concurrent.locks.LockSupport;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class WaitAction implements Action{

    private final long duration;

    public WaitAction(long duration) {
        this.duration = duration;
    }

    @Override
    public void apply(ActionContext context) {
        LockSupport.parkNanos(duration);
    }
}
