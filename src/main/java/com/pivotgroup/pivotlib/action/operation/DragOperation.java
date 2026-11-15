package com.pivotgroup.pivotlib.action.operation;

import com.pivotgroup.pivotlib.action.Operation;
import com.pivotgroup.pivotlib.action.OperationSimulator;
import com.pivotgroup.pivotlib.model.Location;

import java.util.function.Supplier;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/15 17:13
 */

public class DragOperation extends Operation {

    private Supplier<Location> startLocSupplier;

    private Supplier<Location> endLocSupplier;

    private int button;

    private long durationMs;

    private int steps;

    public DragOperation(Supplier<Location> startLocSupplier,
                         Supplier<Location> endLocSupplier,
                         int button, long durationMs,
                         int steps) {
        super("拖拽");
        this.startLocSupplier = startLocSupplier;
        this.endLocSupplier = endLocSupplier;
        this.button = button;
        this.durationMs = durationMs;
        this.steps = steps;
    }

    @Override
    public void operate(OperationSimulator os) {
        Location startLoc = startLocSupplier.get();
        Location endLoc = endLocSupplier.get();
        os.moveDirectly(startLoc.x, startLoc.y);
        os.down(button);
        os.move(endLoc.x, endLoc.y, durationMs, steps);
        os.release(button);
    }
}
