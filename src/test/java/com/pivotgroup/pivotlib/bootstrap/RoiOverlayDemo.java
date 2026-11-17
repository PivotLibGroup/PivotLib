package com.pivotgroup.pivotlib.bootstrap;

import com.pivotgroup.pivotlib.action.gesture.CoordMode;
import com.pivotgroup.pivotlib.action.gesture.MoveGesture;
import com.pivotgroup.pivotlib.core.Pivot;
import com.pivotgroup.pivotlib.model.Dimension;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;
import com.pivotgroup.pivotlib.roi.CoordinateSpace;

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
        pivot.setCoordinateSpace(CoordinateSpace.ROI);

        pivot.setRoi(new Rect(
                new Location(500, 500),
                new Dimension(500, 500)));

        pivot.editRoi();

        pivot.addAction(new MoveGesture(
                () -> new Location(-100, 100),
                CoordMode.RELATIVE,
                1000,
                100
        ));

        pivot.addAction(new MoveGesture(
                () -> new Location(50, 100),
                CoordMode.RELATIVE,
                1000,
                100
        ));

        pivot.addAction(new MoveGesture(
                () -> new Location(-100, 100),
                CoordMode.RELATIVE,
                1000,
                100
        ));

        pivot.addAction(new MoveGesture(
                () -> new Location(-100, -100),
                CoordMode.RELATIVE,
                1000,
                100
        ));

        pivot.addAction(new MoveGesture(
                () -> new Location(-50, -100),
                CoordMode.RELATIVE,
                1000,
                100
        ));

        pivot.start();
    }
}
