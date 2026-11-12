package com.pivotgroup.pivotlib_old.util.coordinate;

public class Loc {

    public final int x;
    public final int y;

    public Loc(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Loc{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}