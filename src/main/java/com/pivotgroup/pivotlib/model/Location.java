package com.pivotgroup.pivotlib.model;

/**
 * Immutable two-dimensional coordinate.
 *
 * @author xinliu
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
        return new Location(Math.addExact(x, xDelta), Math.addExact(y, yDelta));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "Location{" + "x=" + x + ", y=" + y + '}';
    }
}
