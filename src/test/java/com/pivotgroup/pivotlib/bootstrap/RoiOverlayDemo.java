package com.pivotgroup.pivotlib.bootstrap;

import com.pivotgroup.pivotlib.core.Pivot;
import com.pivotgroup.pivotlib.model.Rect;

/**
 * Manual ROI editor entry point.
 *
 * @author xinliu
 */
public final class RoiOverlayDemo {

    private RoiOverlayDemo() {
    }

    public static void main(String[] args) {
        Pivot pivot = DesktopPivot.create();
        Rect roi = pivot.editRoi();
        System.out.println("ROI: " + roi);
    }
}
