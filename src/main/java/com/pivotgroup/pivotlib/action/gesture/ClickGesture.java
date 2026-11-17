package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.action.Gesture;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;

/**
 * Moves to a location and performs a button click.
 *
 * @author xinliu
 */
public class ClickGesture extends Gesture {

    private static final long DEFAULT_PRESS_DURATION_MS = 1_000L;

    private final int button;

    private final long pressDurationMs;

    public ClickGesture(Supplier<Location> locSupplier, int button) {
        this(locSupplier, button, DEFAULT_PRESS_DURATION_MS);
    }

    ClickGesture(Supplier<Location> locSupplier, int button, long pressDurationMs) {
        super(locSupplier);
        if (pressDurationMs < 0) {
            throw new IllegalArgumentException("pressDurationMs must not be negative");
        }
        this.button = button;
        this.pressDurationMs = pressDurationMs;
    }

    @Override
    public void apply(ActionContext context) {
        TouchCapturable touchCapturer = context.getTouchCapturer();
        Location location = resolveLocation();
        touchCapturer.move(location.x, location.y);
        click(touchCapturer);
    }

    private void click(TouchCapturable touchCapturer) {
        touchCapturer.down(button);
        try {
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(pressDurationMs));
        } finally {
            touchCapturer.release(button);
        }
    }
}
