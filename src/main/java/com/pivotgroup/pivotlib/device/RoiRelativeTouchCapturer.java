package com.pivotgroup.pivotlib.device;

import com.pivotgroup.pivotlib.action.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;

import java.util.Objects;

/**
 * Converts ROI-relative coordinates to absolute screen coordinates.
 *
 * @author xinliu
 */
public final class RoiRelativeTouchCapturer implements TouchCapturable {

    private final RoiTouchCapturer delegate;

    private final Rect roi;

    public RoiRelativeTouchCapturer(TouchCapturable delegate, Rect roi) {
        Objects.requireNonNull(delegate, "delegate");
        this.roi = Objects.requireNonNull(roi, "roi");
        this.delegate = new RoiTouchCapturer(delegate, roi);
    }

    @Override
    public void down(int button) {
        delegate.down(button);
    }

    @Override
    public void release(int button) {
        delegate.release(button);
    }

    @Override
    public void move(int x, int y) {
        int screenX = Math.addExact(roi.getX(), x);
        int screenY = Math.addExact(roi.getY(), y);
        delegate.move(screenX, screenY);
    }

    @Override
    public Location getLocation() {
        Location screenLocation = delegate.getLocation();
        return Location.of(
                Math.subtractExact(screenLocation.x, roi.getX()),
                Math.subtractExact(screenLocation.y, roi.getY()));
    }
}
