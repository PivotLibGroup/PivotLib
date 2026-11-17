package com.pivotgroup.pivotlib.device;

import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;

import java.util.Objects;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/17 17:52
 */

public class RoiRelativeTouchCapturer implements TouchCapturable {

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

        int maxScreenX = Math.addExact(roi.getX(), roi.getWidth() - 1);
        int maxScreenY = Math.addExact(roi.getY(), roi.getHeight() - 1);

        int clampedX = Math.max(
                roi.getX(),
                Math.min(screenX, maxScreenX));

        int clampedY = Math.max(
                roi.getY(),
                Math.min(screenY, maxScreenY));

        delegate.move(clampedX, clampedY);
    }

    @Override
    public Location getLocation() {
        Location screenLocation = delegate.getLocation();
        return Location.of(
                Math.subtractExact(screenLocation.x, roi.getX()),
                Math.subtractExact(screenLocation.y, roi.getY())
        );
    }
}
