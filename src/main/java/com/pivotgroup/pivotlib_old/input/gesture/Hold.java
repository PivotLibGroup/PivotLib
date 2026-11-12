package com.pivotgroup.pivotlib_old.input.gesture;

import com.pivotgroup.pivotlib_old.input.Action;
import com.pivotgroup.pivotlib_old.input.ActionResult;
import com.pivotgroup.pivotlib_old.input.InputButton;
import com.pivotgroup.pivotlib_old.input.Locator;
import com.pivotgroup.pivotlib_old.input.Pointer;
import com.pivotgroup.pivotlib_old.util.coordinate.Loc;

import java.util.Optional;

/**
 * 按住不放：按下后停在原地，到时长才松开。
 * 内部按 sliceMs 切片，每片检查一次中断，被中断时立刻松手并返回失败——
 * Tap(pressMs=5000) 做不到这一点，按住期间是聋的。
 */
public final class Hold implements Action {

    public static final long DEFAULT_SLICE_MS = 100;

    private final Locator target;
    private final InputButton button;
    private final long durationMs;
    private final long sliceMs;

    public Hold(int x, int y, InputButton button, long durationMs) {
        this(new Loc(x, y), button, durationMs);
    }

    public Hold(Loc at, InputButton button, long durationMs) {
        this(Locator.at(at), button, durationMs);
    }

    public Hold(Loc at, InputButton button, long durationMs, long sliceMs) {
        this(Locator.at(at), button, durationMs, sliceMs);
    }

    public Hold(int x, int y, InputButton button, long durationMs, long sliceMs) {
        this(Locator.at(new Loc(x, y)), button, durationMs, sliceMs);
    }

    public Hold(Locator target, InputButton button, long durationMs) {
        this(target, button, durationMs, DEFAULT_SLICE_MS);
    }

    public Hold(Locator target, InputButton button, long durationMs, long sliceMs) {
        this.target = target;
        this.button = button;
        this.durationMs = durationMs;
        this.sliceMs = Math.max(1, sliceMs);
    }

    @Override
    public ActionResult exec(Pointer pointer) {
        Optional<Loc> at = target.find();
        if (at.isEmpty()) {
            return ActionResult.fail("Hold 未找到目标 " + target);
        }

        long startNs = System.nanoTime();
        pointer.move(at.get());
        pointer.press(button);
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    long held = (System.nanoTime() - startNs) / 1_000_000L;
                    return ActionResult.fail("Hold 被中断，已按住 " + held + "ms / " + durationMs + "ms");
                }
                long remain = durationMs - (System.nanoTime() - startNs) / 1_000_000L;
                if (remain <= 0) {
                    break;
                }
                pointer.sleep(Math.min(remain, sliceMs));
            }
            return ActionResult.ok(at.get());
        } finally {
            pointer.release(button);
        }
    }

    @Override
    public String toString() {
        return "Hold(" + target + ", " + button + ", " + durationMs + "ms/" + sliceMs + "ms)";
    }
}
