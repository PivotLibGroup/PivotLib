package com.pivotgroup.pivotlib.action.enums;

import com.pivotgroup.pivotlib.model.Location;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Strategy for resolving absolute or relative gesture coordinates.
 *
 * @author xinliu
 */
public enum CoordMode {
    /** Uses the supplied location as an absolute screen coordinate. */
    ABSOLUTE {
        @Override
        public Location resolve(Supplier<Location> target, Location current) {
            Objects.requireNonNull(current, "current");
            return Objects.requireNonNull(target.get(), "target.get()");
        }
    },
    /** Uses the supplied location as a delta from the current pointer. */
    RELATIVE {
        @Override
        public Location resolve(Supplier<Location> target, Location current) {
            Objects.requireNonNull(current, "current");
            Location delta = Objects.requireNonNull(target.get(), "target.get()");
            return current.offset(delta.x, delta.y);
        }
    };

    /**
     * Resolves a target location using the supplied coordinate strategy.
     *
     * @param target absolute target or relative delta supplier
     * @param current current pointer location
     * @return resolved absolute target
     */
    public abstract Location resolve(Supplier<Location> target, Location current);
}
