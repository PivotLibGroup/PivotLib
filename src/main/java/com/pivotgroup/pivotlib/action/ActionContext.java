package com.pivotgroup.pivotlib.action;

import java.util.Objects;

/**
 * Dependencies available to an action execution.
 *
 * @author xinliu
 */
public final class ActionContext {

    private final TouchCapturable touchCapturer;

    /**
     * Creates an action context.
     *
     * @param touchCapturer touch device
     */
    public ActionContext(TouchCapturable touchCapturer) {
        this.touchCapturer = Objects.requireNonNull(touchCapturer, "touchCapturer");
    }

    /**
     * Returns the touch device associated with this execution.
     *
     * @return touch device
     */
    public TouchCapturable getTouchCapturer() {
        return touchCapturer;
    }
}
