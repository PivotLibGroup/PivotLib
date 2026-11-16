package com.pivotgroup.pivotlib.action.operation;

import com.pivotgroup.pivotlib.action.CoordMode;
import com.pivotgroup.pivotlib.action.Operation;
import com.pivotgroup.pivotlib.action.simulator.DesktopOperationSimulator;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;

import java.util.function.Supplier;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 19:11
 */

public class MoveOperation extends Operation {

    private Supplier<Location> locSupplier;

    private CoordMode coordMode;

    private long durationMs;

    private int steps;

    private boolean directly = false;

    public MoveOperation(Supplier<Location> locSupplier,
                         CoordMode coordMode) {
        this(locSupplier, coordMode, 0, 0);
        directly = true;
    }

    public MoveOperation(Supplier<Location> locSupplier,
                         CoordMode coordMode, long durationMs,
                         int steps) {
        this();
        this.locSupplier = locSupplier;
        this.coordMode = coordMode;
        this.durationMs = durationMs;
        this.steps = steps;
    }

    private MoveOperation() {
        super("移动");
    }

    @Override
    public void operate(DesktopOperationSimulator os) {
        Location l = locSupplier.get();
        switch (coordMode) {
            case ABSOLUTE -> linearOrDirectlyMove(os, l.x, l.y);
            case RELATIVE -> {
                TouchCapturable tc = os.getTouchCapturer();
                Location touchLoc = tc.getLocation();
                linearOrDirectlyMove(os, touchLoc.x + l.x, touchLoc.y + l.y);
            }
        }
    }

    private void linearOrDirectlyMove(DesktopOperationSimulator os, int x, int y) {
        if (directly) {
            os.move(x, y,0,0);
            return;
        }
        os.move(x, y, durationMs, steps);
    }
}