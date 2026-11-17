package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.action.BiGesture;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;

import java.util.function.Supplier;

/**
 * Drags between two locations while holding a button.
 *
 * @author xinliu
 */
public class DragGesture extends BiGesture {

    private final LinearMotion motion;

    private final int button;

    public DragGesture(Supplier<Location> startLoc, Supplier<Location> endLocSupplier,
                       int button, long durationMs, int steps) {
        super(startLoc, endLocSupplier);
        this.button = button;
        motion = new LinearMotion(durationMs, steps);
    }

    @Override
    public void apply(ActionContext context) {
        TouchCapturable touchCapturer = context.getTouchCapturer();
        Location start = resolveFirstLocation();
        Location end = resolveSecondLocation();
        touchCapturer.move(start.x, start.y);
        touchCapturer.down(button);
        try {
            motion.move(touchCapturer, end);
        } finally {
            touchCapturer.release(button);
        }
    }
}
