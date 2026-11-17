package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.model.Location;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Base class for gestures with start and end locations.
 *
 * @author xinliu
 */
public abstract class BasePathGesture implements Action {

    private final Supplier<Location> start;

    private final Supplier<Location> end;

    protected BasePathGesture(Supplier<Location> start, Supplier<Location> end) {
        this.start = Objects.requireNonNull(start, "start");
        this.end = Objects.requireNonNull(end, "end");
    }

    public final Supplier<Location> getStart() {
        return start;
    }

    public final Supplier<Location> getEnd() {
        return end;
    }

    protected final Location resolveStart() {
        return Objects.requireNonNull(start.get(), "start.get()");
    }

    protected final Location resolveEnd() {
        return Objects.requireNonNull(end.get(), "end.get()");
    }
}
