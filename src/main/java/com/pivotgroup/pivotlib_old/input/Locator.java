package com.pivotgroup.pivotlib_old.input;

import com.pivotgroup.pivotlib_old.util.coordinate.Loc;

import java.util.Optional;

@FunctionalInterface
public interface Locator {

    Optional<Loc> find();

    static Locator at(Loc loc) {
        return new Locator() {
            @Override
            public Optional<Loc> find() {
                return Optional.of(loc);
            }

            @Override
            public String toString() {
                return "at(" + loc + ")";
            }
        };
    }

    static Locator offset(Locator base, int dx, int dy) {
        return new Locator() {
            @Override
            public Optional<Loc> find() {
                return base.find().map(l -> new Loc(l.x + dx, l.y + dy));
            }

            @Override
            public String toString() {
                return "offset(" + base + ", " + dx + ", " + dy + ")";
            }
        };
    }

    static Locator retry(Locator base, long timeoutMs, long intervalMs) {
        return new Locator() {
            @Override
            public Optional<Loc> find() {
                long deadline = System.currentTimeMillis() + timeoutMs;
                while (true) {
                    Optional<Loc> hit = base.find();
                    if (hit.isPresent()) {
                        return hit;
                    }
                    if (System.currentTimeMillis() >= deadline) {
                        return Optional.empty();
                    }
                    try {
                        Thread.sleep(Math.max(1, intervalMs));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return Optional.empty();
                    }
                }
            }

            @Override
            public String toString() {
                return "retry(" + base + ", " + timeoutMs + "ms/" + intervalMs + "ms)";
            }
        };
    }
}
