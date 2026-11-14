package com.pivotgroup.pivotlib.action;

import com.pivotgroup.pivotlib.model.Location;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 19:11
 */

public class MoveOperation implements Operable {

    public enum Type {
        RELATIVE, DIRECTLY
    }

    private Location loc;

    private Type type;

    private long durationMs;

    private int steps;

    public MoveOperation(int x, int y,long durationMs, Type type, int steps) {
        this(new Location(x, y), durationMs, type, steps);
    }

    public MoveOperation(Location loc, long durationMs, Type type, int steps) {
        this.loc = loc;
        this.type = type;
        this.durationMs = durationMs;
        this.steps = steps;
    }

    @Override
    public void operate(OperationSimulator operationSimulator) {
        switch (type) {
            case RELATIVE -> operationSimulator.move(loc.x, loc.y, durationMs, steps);
            case DIRECTLY -> operationSimulator.moveDirectly(loc.x, loc.y);
        }
    }
}