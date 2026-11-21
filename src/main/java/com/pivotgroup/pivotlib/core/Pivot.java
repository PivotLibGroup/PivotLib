package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.action.ActionSequence;
import com.pivotgroup.pivotlib.action.TouchCapturable;
import com.pivotgroup.pivotlib.device.ImageCapturable;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;
import com.pivotgroup.pivotlib.roi.CoordinateSpace;
import com.pivotgroup.pivotlib.roi.RoiState;
import com.pivotgroup.pivotlib.ui.roi.RoiEditor;
import com.pivotgroup.pivotlib.ui.roi.RoiOverlay;
import com.pivotgroup.pivotlib.vision.Identifier;
import com.pivotgroup.pivotlib.vision.Point;
import com.pivotgroup.pivotlib.vision.opencv.OpenCVIdentifier;
import org.opencv.core.Mat;

import java.util.Objects;
import java.util.Optional;

/**
 * Facade for image recognition, gestures, and ROI editing.
 *
 * @author xinliu
 */
public class Pivot {

    private final TouchCapturable touchCapturer;

    private final Identifier identifier;

    private final ActionSequence actions;

    private final RoiState roiState;

    private final RoiEditor roiEditor;

    private final TemplateRepository templates;

    /**
     * Creates an automation facade with platform-specific devices.
     *
     * @param touchCapturer touch device
     * @param imageCapturer image device
     */
    public Pivot(TouchCapturable touchCapturer, ImageCapturable imageCapturer) {
        this.touchCapturer = Objects.requireNonNull(touchCapturer, "touchCapturer");
        identifier = new OpenCVIdentifier();
        actions = new ActionSequence();
        roiState = new RoiState();
        roiEditor = new RoiEditor(roiState);
        templates = new TemplateRepository();
    }

    /**
     * Restricts recognition and input to the specified region.
     *
     * @param roi region of interest
     */
    public void setRoi(Rect roi) {
        roiState.set(roi);
    }

    /**
     * Removes the region restriction.
     */
    public void clearRoi() {
        roiState.clear();
    }

    /**
     * Shows the ROI editor and returns after the window becomes visible.
     *
     * @return visible ROI editor window
     */
    public RoiOverlay showRoiOverlay() {
        return roiEditor.show();
    }

    /**
     * Opens the ROI editor and waits until editing finishes.
     *
     * @return confirmed ROI, or {@code null} when a new ROI is cancelled
     */
    public Rect editRoi() {
        return roiEditor.edit();
    }

    /**
     *
     * @param coordinateSpace ROI's action coordinate
     */
    public void setCoordinateSpace(CoordinateSpace coordinateSpace) {
        roiState.setCoordinateSpace(coordinateSpace);
    }

    /**
     * Executes actions in insertion order.
     */
    public void start() {
        ActionContext actionContext = new ActionContext(roiState.decorate(touchCapturer));
        actions.apply(actionContext);
    }

    /**
     * Appends an action to the execution sequence.
     *
     * @param action action to append
     */
    public void addAction(Action action) {
        actions.add(action);
    }

    /**
     * Locates an image template within the current ROI.
     *
     * @param templ template image
     * @return location in the configured coordinate space
     */
    public Location locate(Mat templ) {
        Optional<Point> identify = identifier.identify();

    }

}
