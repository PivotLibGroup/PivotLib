package com.pivotgroup.pivotlib.device;

import com.pivotgroup.pivotlib.action.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;

import java.util.Objects;

/**
 * Touch decorator that rejects operations outside the current ROI.
 *
 * @author xinliu
 */
public final class RoiTouchCapturer implements TouchCapturable {

    private final TouchCapturable delegate;

    private final Rect roi;

    public RoiTouchCapturer(TouchCapturable delegate, Rect roi) {
        this.delegate = Objects.requireNonNull(delegate, "delegate");
        this.roi = Objects.requireNonNull(roi, "roi");
    }

    @Override
    public void down(int button) {
        Location location = delegate.getLocation();
        if (!roi.contains(location)) {
            throw new IllegalStateException("Cannot press at " + location + " outside ROI " + roi);
        }
        delegate.down(button);
    }

    @Override
    public void release(int button) {
        delegate.release(button);
    }

    @Override
    public void move(int x, int y) {
        if (!roi.contains(x, y)) {
            throw new IllegalArgumentException("Location " + Location.of(x, y) + " is outside ROI " + roi);
        }
        delegate.move(x, y);
    }

    @Override
    public Location getLocation() {
        return delegate.getLocation();
    }
}
