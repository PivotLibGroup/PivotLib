package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.action.ActionSequence;
import com.pivotgroup.pivotlib.device.ImageCapturable;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;
import com.pivotgroup.pivotlib.model.ResultLoc;
import com.pivotgroup.pivotlib.roi.CoordinateSpace;
import com.pivotgroup.pivotlib.roi.RoiState;
import com.pivotgroup.pivotlib.ui.RoiEditor;
import com.pivotgroup.pivotlib.ui.RoiOverlay;
import com.pivotgroup.pivotlib.vision.Identifier;
import org.opencv.core.Mat;

import java.util.NoSuchElementException;
import java.util.Objects;

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
        identifier = new Identifier(imageCapturer);
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
        ActionContext context = new ActionContext(roiState.decorate(touchCapturer));
        actions.apply(context);
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
     * Registers a named image template.
     *
     * @param name template name
     * @param identifiedMat template image
     */
    public void putIdentified(String name, Mat identifiedMat) {
        templates.put(name, identifiedMat);
    }

    /**
     * Locates a registered image template.
     *
     * @param name template name
     * @return location in the configured coordinate space
     */
    public Location locate(String name) {
        return locate(templates.get(name));
    }

    /**
     * Locates an image template within the current ROI.
     *
     * @param templ template image
     * @return location in the configured coordinate space
     */
    public Location locate(Mat templ) {
        Rect currentRoi = roiState.get().orElse(null);
        ResultLoc result = currentRoi == null
                ? identifier.identify(templ)
                : identifier.identify(templ, currentRoi);
        if (result.isPresent()) {
            return toActionLocation(result.loc, currentRoi);
        }
        throw new NoSuchElementException("The template does not exist on the canvas");
    }

    private Location toActionLocation(Location screenLocation, Rect currentRoi) {
        if (currentRoi != null && roiState.getCoordinateSpace() == CoordinateSpace.ROI) {
            return Location.of(
                    Math.subtractExact(screenLocation.x, currentRoi.getX()),
                    Math.subtractExact(screenLocation.y, currentRoi.getY()));
        }
        return screenLocation;
    }
}
