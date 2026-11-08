package com.pivotgroup.pivotlib.util;

public class Loc {

    public final double matchResult;
    public final int x;
    public final int y;

    public Loc(double matchResult, int x, int y) {
        this.matchResult = matchResult;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("score=%.4f at (%d, %d)", matchResult, x, y);
    }
}