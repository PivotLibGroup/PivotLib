package com.pivotgroup.pivotlib.action;

import com.pivotgroup.pivotlib.model.Location;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Base class for actions with two location suppliers.
 *
 * @author xinliu
 */
@SuppressWarnings("PMD.AbstractClassShouldStartWithAbstractNamingRule")
public abstract class BiGesture implements Action {

    private final Supplier<Location> firstLoc;

    private final Supplier<Location> secondLoc;

    protected BiGesture(Supplier<Location> firstLoc, Supplier<Location> secondLoc) {
        this.firstLoc = Objects.requireNonNull(firstLoc, "firstLoc");
        this.secondLoc = Objects.requireNonNull(secondLoc, "secondLoc");
    }

    public Supplier<Location> getFirstLoc() {
        return firstLoc;
    }

    public Supplier<Location> getSecondLoc() {
        return secondLoc;
    }

    protected final Location resolveFirstLocation() {
        return Objects.requireNonNull(firstLoc.get(), "firstLoc.get()");
    }

    protected final Location resolveSecondLocation() {
        return Objects.requireNonNull(secondLoc.get(), "secondLoc.get()");
    }
}
