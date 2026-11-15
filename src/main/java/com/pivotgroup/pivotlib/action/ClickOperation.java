package com.pivotgroup.pivotlib.action;

import com.pivotgroup.pivotlib.model.Location;

import java.util.function.Supplier;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 18:56
 */

public class ClickOperation extends Operation {

    private int button;

    private long durationMs;

    private Supplier<Location> locSupplier;

    private boolean directlyClick;

    public ClickOperation(
            Supplier<Location> locSupplier,
            int button,
            long durationMs) {
        this(button, durationMs);
        this.locSupplier = locSupplier;
        directlyClick = false;
    }

    public ClickOperation(int button, long durationMs) {
        super("点击");
        this.button = button;
        this.durationMs = durationMs;
        directlyClick = true;
    }

    @Override
    public void operate(OperationSimulator os) {
        if (!directlyClick) {
            Location location = locSupplier.get();
            if (location == null) {
                return;
            }
            os.moveDirectly(location.x, location.y);
        }
        os.click(button, durationMs);
    }
}
