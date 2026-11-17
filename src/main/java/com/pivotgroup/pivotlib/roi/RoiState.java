package com.pivotgroup.pivotlib.roi;

import com.pivotgroup.pivotlib.device.RoiRelativeTouchCapturer;
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

    private volatile CoordinateSpace coordinateSpace = CoordinateSpace.SCREEN;

    public void set(Rect roi) {
        this.roi = Objects.requireNonNull(roi, "roi");
    }

    public void clear() {
        roi = null;
    }

    public Optional<Rect> get() {
        return Optional.ofNullable(roi);
    }

    public void setCoordinateSpace(CoordinateSpace coordinateSpace) {
        this.coordinateSpace = Objects.requireNonNull(
                coordinateSpace, "coordinateSpace");
    }

    public CoordinateSpace getCoordinateSpace() {
        return coordinateSpace;
    }

    public TouchCapturable decorate(TouchCapturable touchCapturer) {
        Objects.requireNonNull(touchCapturer, "touchCapturer");

        Rect currentRoi = roi;
        CoordinateSpace currentSpace = coordinateSpace;

        if (currentRoi == null) {
            if (currentSpace == CoordinateSpace.ROI) {
                throw new IllegalStateException(
                        "ROI coordinate space requires an ROI");
            }
            return touchCapturer;
        }

        return switch (currentSpace) {
            case SCREEN -> new RoiTouchCapturer(touchCapturer, currentRoi);
            case ROI -> new RoiRelativeTouchCapturer(touchCapturer, currentRoi);
        };
    }
}
