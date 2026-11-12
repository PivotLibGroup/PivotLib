package com.pivotgroup.pivotlib_old.input.gesture;

import com.pivotgroup.pivotlib_old.input.Action;
import com.pivotgroup.pivotlib_old.input.ActionResult;
import com.pivotgroup.pivotlib_old.input.InputButton;
import com.pivotgroup.pivotlib_old.input.Locator;
import com.pivotgroup.pivotlib_old.input.Pointer;
import com.pivotgroup.pivotlib_old.util.coordinate.Loc;

import java.util.Optional;

public final class Tap implements Action {

    private final Locator target;
    private final InputButton button;
    private final long pressMs;

    public Tap(int x, int y, InputButton button, long pressMs) {
        this(new Loc(x, y), button, pressMs);
    }

    public Tap(Loc at, InputButton button, long pressMs) {
        this(Locator.at(at), button, pressMs);
    }

    public Tap(Locator target, InputButton button, long pressMs) {
        this.target = target;
        this.button = button;
        this.pressMs = pressMs;
    }

    @Override
    public ActionResult exec(Pointer pointer) {
        Optional<Loc> at = target.find();
        if (at.isEmpty()) {
            return ActionResult.fail("Tap 未找到目标 " + target);
        }
        pointer.tap(at.get(), button, pressMs);
        return ActionResult.ok(at.get());
    }
}
