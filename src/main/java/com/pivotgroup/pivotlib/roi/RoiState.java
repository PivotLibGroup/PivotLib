package com.pivotgroup.pivotlib.roi;

import com.pivotgroup.pivotlib.device.RoiTouchCapturer;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Rect;

import java.util.Objects;
import java.util.Optional;

/**
 * Holds the current region of interest and applies it to input devices.
 *
 * @author xinliu
 */
public final class RoiState {

    private volatile Rect roi;

    public void set(Rect roi) {
        this.roi = Objects.requireNonNull(roi, "roi");
    }

    public void clear() {
        roi = null;
    }

    public Optional<Rect> get() {
        return Optional.ofNullable(roi);
    }

    public TouchCapturable decorate(TouchCapturable touchCapturer) {
        Objects.requireNonNull(touchCapturer, "touchCapturer");
        Rect currentRoi = roi;
        return currentRoi == null ? touchCapturer : new RoiTouchCapturer(touchCapturer, currentRoi);
    }
}
