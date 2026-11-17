package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.action.Gesture;
import com.pivotgroup.pivotlib.action.enums.CoordMode;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Moves the pointer using linear interpolation.
 *
 * @author xinliu
 */
public class MoveGesture extends Gesture {

    private final CoordMode coordMode;

    private final LinearMotion motion;

    public MoveGesture(Supplier<Location> toLoc, CoordMode coordMode, long durationMs, int steps) {
        super(toLoc);
        this.coordMode = Objects.requireNonNull(coordMode, "coordMode");
        motion = new LinearMotion(durationMs, steps);
    }

    @Override
    public void apply(ActionContext context) {
        TouchCapturable touchCapturer = context.getTouchCapturer();
        Location target = coordMode.resolve(getLoc(), touchCapturer.getLocation());
        move(touchCapturer, target);
    }

    protected void move(TouchCapturable touchCapturer, Location toLoc) {
        motion.move(touchCapturer, toLoc);
    }
}
