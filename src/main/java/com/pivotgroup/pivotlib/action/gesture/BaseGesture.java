package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.model.Location;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Base class for gestures with one target location.
 *
 * @author xinliu
 */
public abstract class BaseGesture implements Action {

    private final Supplier<Location> target;

    protected BaseGesture(Supplier<Location> target) {
        this.target = Objects.requireNonNull(target, "target");
    }

    public final Supplier<Location> getTarget() {
        return target;
    }

    protected final Location resolveTarget() {
        return Objects.requireNonNull(target.get(), "target.get()");
    }
}
