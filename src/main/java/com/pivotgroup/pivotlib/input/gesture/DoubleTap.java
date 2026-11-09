package com.pivotgroup.pivotlib.input.gesture;

import com.pivotgroup.pivotlib.input.Action;
import com.pivotgroup.pivotlib.input.ActionResult;
import com.pivotgroup.pivotlib.input.InputButton;
import com.pivotgroup.pivotlib.input.Locator;
import com.pivotgroup.pivotlib.input.Pointer;
import com.pivotgroup.pivotlib.util.coordinate.Loc;

import java.util.Optional;

public final class DoubleTap implements Action {

    private final Locator target;
    private final InputButton button;
    private final long pressMs;
    private final long intervalMs;

    public DoubleTap(Loc at, InputButton button, long pressMs, long intervalMs) {
        this(Locator.at(at), button, pressMs, intervalMs);
    }

    public DoubleTap(Locator target, InputButton button, long pressMs, long intervalMs) {
        this.target = target;
        this.button = button;
        this.pressMs = pressMs;
        this.intervalMs = intervalMs;
    }

    @Override
    public ActionResult exec(Pointer pointer) {
        Optional<Loc> at = target.find();
        if (at.isEmpty()) {
            return ActionResult.fail("DoubleTap 未找到目标 " + target);
        }
        pointer.doubleTap(at.get(), button, pressMs, intervalMs);
        return ActionResult.ok(at.get());
    }
}
