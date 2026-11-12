package com.pivotgroup.pivotlib_old.workflow.step;

import com.pivotgroup.pivotlib_old.input.Locator;
import com.pivotgroup.pivotlib_old.workflow.Step;
import com.pivotgroup.pivotlib_old.workflow.WorkflowContext;

/**
 * 等某个目标出现，等到就往下走，等不到就判失败。
 * 和 WaitStep 的区别：WaitStep 是无条件死等，不知道自己在等什么；这个会一直看屏幕。
 */
public class WaitForStep extends Step {

    public static final long DEFAULT_INTERVAL_MS = 100;

    private final Locator target;
    private final long timeoutMs;
    private final long intervalMs;

    public WaitForStep(Locator target, long timeoutMs) {
        this(target, timeoutMs, DEFAULT_INTERVAL_MS);
    }

    public WaitForStep(Locator target, long timeoutMs, long intervalMs) {
        this.target = target;
        this.timeoutMs = timeoutMs;
        this.intervalMs = Math.max(1, intervalMs);
    }

    @Override
    public void exec(WorkflowContext context) {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (true) {
            if (context.isStopping()) {
                return;
            }
            if (target.find().isPresent()) {
                return;
            }
            long remain = deadline - System.currentTimeMillis();
            if (remain <= 0) {
                break;
            }
            context.pointer().sleep(Math.min(remain, intervalMs));
        }
        String message = "等待超时 " + timeoutMs + "ms: " + target;
        System.out.println("[FAIL] " + message);
        context.fail(message);
        context.stop();
    }

    @Override
    public String toString() {
        return "WaitFor(" + target + ", " + timeoutMs + "ms)";
    }
}
