package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Moves the pointer using linear interpolation.
 *
 * @author xinliu
 */
public class MoveGesture extends BaseGesture {

    private final CoordMode coordMode;

    private final LinearMotion motion;

    public MoveGesture(Supplier<Location> target, CoordMode coordMode, long durationMs, int steps) {
        super(target);
        this.coordMode = Objects.requireNonNull(coordMode, "coordMode");
        motion = new LinearMotion(durationMs, steps);
    }

    @Override
    public void apply(ActionContext context) {
        TouchCapturable touchCapturer = context.getTouchCapturer();
        Location target = coordMode.resolve(getTarget(), touchCapturer.getLocation());
        move(touchCapturer, target);
    }

    protected void move(TouchCapturable touchCapturer, Location toLoc) {
        motion.move(touchCapturer, toLoc);
    }
}
