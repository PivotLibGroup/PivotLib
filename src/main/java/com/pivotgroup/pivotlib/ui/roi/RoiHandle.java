package com.pivotgroup.pivotlib.ui.roi;

/**
 * ROI resize handles.
 *
 * @author xinliu
 */
public enum RoiHandle {
    /** Top-left corner. */
    NORTH_WEST(true, true),
    /** Top-right corner. */
    NORTH_EAST(false, true),
    /** Bottom-left corner. */
    SOUTH_WEST(true, false),
    /** Bottom-right corner. */
    SOUTH_EAST(false, false);

    private final boolean left;

    private final boolean top;

    RoiHandle(boolean left, boolean top) {
        this.left = left;
        this.top = top;
    }

    boolean isLeft() {
        return left;
    }

    boolean isTop() {
        return top;
    }
}
