package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.action.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;

/**
 * Moves to a location and performs a button click.
 *
 * @author xinliu
 */
public class ClickGesture extends BaseGesture {

    private static final long DEFAULT_PRESS_DURATION_MS = 500L;

    private final int button;

    private final long pressDurationMs;

    public ClickGesture(Supplier<Location> target, int button) {
        this(target, button, DEFAULT_PRESS_DURATION_MS);
    }

    ClickGesture(Supplier<Location> target, int button, long pressDurationMs) {
        super(target);
        if (pressDurationMs < 0) {
            throw new IllegalArgumentException("pressDurationMs must not be negative");
        }
        this.button = button;
        this.pressDurationMs = pressDurationMs;
    }

    @Override
    public void apply(ActionContext context) {
        TouchCapturable touchCapturer = context.getTouchCapturer();
        Location location = resolveTarget();
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
