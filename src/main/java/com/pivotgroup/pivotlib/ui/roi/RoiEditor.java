package com.pivotgroup.pivotlib.ui.roi;

import com.pivotgroup.pivotlib.model.Rect;
import com.pivotgroup.pivotlib.roi.RoiState;

import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Coordinates the ROI editor lifecycle independently of Swing widgets.
 *
 * @author xinliu
 */
public final class RoiEditor {

    private final RoiState roiState;

    private RoiOverlay roiOverlay;

    public RoiEditor(RoiState roiState) {
        this.roiState = Objects.requireNonNull(roiState, "roiState");
    }

    public RoiOverlay show() {
        if (SwingUtilities.isEventDispatchThread()) {
            return showOnEdt();
        }
        AtomicReference<RoiOverlay> overlayReference = new AtomicReference<>();
        try {
            SwingUtilities.invokeAndWait(() -> overlayReference.set(showOnEdt()));
            return overlayReference.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while opening ROI overlay", e);
        } catch (InvocationTargetException e) {
            return rethrow(e.getCause());
        }
    }

    public Rect edit() {
        if (SwingUtilities.isEventDispatchThread()) {
            throw new IllegalStateException("editRoi must not be called on the EDT");
        }
        RoiOverlay overlay = show();
        overlay.awaitClose();
        return roiState.get().orElse(null);
    }

    private RoiOverlay showOnEdt() {
        if (roiOverlay != null && roiOverlay.isDisplayable()) {
            roiOverlay.activate();
            return roiOverlay;
        }
        Rect initialRoi = roiState.get().orElse(null);
        roiOverlay = new RoiOverlay(initialRoi, roiState::set, () -> {
            if (initialRoi == null) {
                roiState.clear();
            } else {
                roiState.set(initialRoi);
            }
        });
        roiOverlay.setVisible(true);
        return roiOverlay;
    }

    private static RoiOverlay rethrow(Throwable cause) {
        if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        }
        if (cause instanceof Error) {
            throw (Error) cause;
        }
        throw new IllegalStateException("Failed to open ROI overlay", cause);
    }
}
