package com.pivotgroup.pivotlib.action;

import com.pivotgroup.pivotlib.model.Location;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Base class for actions with one location supplier.
 *
 * @author xinliu
 */
@SuppressWarnings("PMD.AbstractClassShouldStartWithAbstractNamingRule")
public abstract class Gesture implements Action {

    private final Supplier<Location> loc;

    protected Gesture(Supplier<Location> loc) {
        this.loc = Objects.requireNonNull(loc, "loc");
    }

    public Supplier<Location> getLoc() {
        return loc;
    }

    protected final Location resolveLocation() {
        return Objects.requireNonNull(loc.get(), "loc.get()");
    }
}
