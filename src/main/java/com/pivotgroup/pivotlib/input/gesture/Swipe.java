package com.pivotgroup.pivotlib.input.gesture;

import com.pivotgroup.pivotlib.input.Action;
import com.pivotgroup.pivotlib.input.ActionResult;
import com.pivotgroup.pivotlib.input.InputButton;
import com.pivotgroup.pivotlib.input.Locator;
import com.pivotgroup.pivotlib.input.Pointer;
import com.pivotgroup.pivotlib.util.coordinate.Loc;

import java.util.Optional;

public final class Swipe implements Action {

    private final Locator from;
    private final Locator to;
    private final int dx;
    private final int dy;
    private final InputButton button;
    private final long totalMs;
    private final int steps;

    public Swipe(Loc from, Loc to, InputButton button, long totalMs, int steps) {
        this(Locator.at(from), Locator.at(to), button, totalMs, steps);
    }

    public Swipe(Locator from, Locator to, InputButton button, long totalMs, int steps) {
        this.from = from;
        this.to = to;
        this.dx = 0;
        this.dy = 0;
        this.button = button;
        this.totalMs = totalMs;
        this.steps = steps;
    }

    private Swipe(Locator from, int dx, int dy, InputButton button, long totalMs, int steps) {
        this.from = from;
        this.to = null;
        this.dx = dx;
        this.dy = dy;
        this.button = button;
        this.totalMs = totalMs;
        this.steps = steps;
    }

    public static Swipe offsetBy(Locator from, int dx, int dy,
                                 InputButton button, long totalMs, int steps) {
        return new Swipe(from, dx, dy, button, totalMs, steps);
    }

    @Override
    public ActionResult exec(Pointer pointer) {
        Optional<Loc> a = from.find();
        if (a.isEmpty()) {
            return ActionResult.fail("Swipe 起点未找到 " + from);
        }
        Loc start = a.get();

        Loc end;
        if (to != null) {
            Optional<Loc> b = to.find();
            if (b.isEmpty()) {
                return ActionResult.fail("Swipe 终点未找到 " + to);
            }
            end = b.get();
        } else {
            end = new Loc(start.x + dx, start.y + dy);
        }

        pointer.swipe(start, end, button, totalMs, steps);
        return ActionResult.ok(start);
    }
}
