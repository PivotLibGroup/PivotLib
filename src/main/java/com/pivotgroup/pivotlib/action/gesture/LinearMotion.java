package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

final class LinearMotion {

    private final long durationMs;

    private final int steps;

    LinearMotion(long durationMs, int steps) {
        if (durationMs < 0) {
            throw new IllegalArgumentException("durationMs must not be negative");
        }
        this.durationMs = durationMs;
        this.steps = Math.max(1, steps);
    }

    void move(TouchCapturable touchCapturer, Location target) {
        Objects.requireNonNull(touchCapturer, "touchCapturer");
        Objects.requireNonNull(target, "target");
        Location origin = Objects.requireNonNull(touchCapturer.getLocation(), "touchCapturer.getLocation()");
        long startedAt = System.nanoTime();
        for (int step = 1; step <= steps; step++) {
            if (Thread.currentThread().isInterrupted()) {
                throw new IllegalStateException("Movement interrupted");
            }
            waitForStep(startedAt, step);
            long deltaX = (long) target.x - origin.x;
            long deltaY = (long) target.y - origin.y;
            int x = (int) Math.round(origin.x + deltaX * (double) step / steps);
            int y = (int) Math.round(origin.y + deltaY * (double) step / steps);
            touchCapturer.move(x, y);
        }
    }

    private void waitForStep(long startedAt, int step) {
        long elapsedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startedAt);
        long remainingMs = durationMs * step / steps - elapsedMs;
        if (remainingMs <= 0) {
            return;
        }
        try {
            Thread.sleep(remainingMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Movement interrupted", e);
        }
    }
}
