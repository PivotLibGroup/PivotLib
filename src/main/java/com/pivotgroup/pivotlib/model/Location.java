package com.pivotgroup.pivotlib.model;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 18:35
 */

public class Location {

    public final int x;

    public final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Location of(int x, int y) {
        return new Location(x, y);
    }

    public Location offset(int xDelta, int yDelta) {
        return new Location(x + xDelta, y + yDelta);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
