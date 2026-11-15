package com.pivotgroup.pivotlib.action;

import com.pivotgroup.pivotlib.model.Location;

import java.util.function.Supplier;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 19:11
 */

public class MoveOperation extends Operation {

    public enum Type {
        RELATIVE, DIRECTLY
    }

    private Supplier<Location> locSupplier;

    private Type type;

    private long durationMs;

    private int steps;

    public MoveOperation(Supplier<Location> locSupplier,
                         Type type, long durationMs,
                         int steps) {
        this();
        this.locSupplier = locSupplier;
        this.type = type;
        this.durationMs = durationMs;
        this.steps = steps;
    }

    private MoveOperation() {
        super("移动");
    }

    @Override
    public void operate(OperationSimulator operationSimulator) {
        Location l = locSupplier.get();
        switch (type) {
            case RELATIVE -> operationSimulator.move(l.x, l.y, durationMs, steps);
            case DIRECTLY -> operationSimulator.moveDirectly(l.x, l.y);
        }
    }
}