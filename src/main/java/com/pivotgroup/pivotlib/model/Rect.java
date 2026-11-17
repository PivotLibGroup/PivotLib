package com.pivotgroup.pivotlib.model;

import java.util.Objects;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/15 22:10
 */

public final class Rect {

    private final Location location;

    private final Dimension dimension;

    public Rect(Location location, Dimension dimension) {
        this.location = Objects.requireNonNull(location, "location");
        this.dimension = Objects.requireNonNull(dimension, "dimension");
        if (dimension.getWidth() <= 0 || dimension.getHeight() <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
    }

    public static Rect of(int x, int y, int width, int height) {
        return new Rect(Location.of(x, y), Dimension.of(width, height));
    }

    public Location getLocation() {
        return location;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public int getX() {
        return location.x;
    }

    public int getY() {
        return location.y;
    }

    public int getWidth() {
        return dimension.getWidth();
    }

    public int getHeight() {
        return dimension.getHeight();
    }

    public boolean contains(Location location) {
        Objects.requireNonNull(location, "location");
        return contains(location.x, location.y);
    }

    public boolean contains(int x, int y) {
        return x >= getX() && x < (long) getX() + getWidth()
                && y >= getY() && y < (long) getY() + getHeight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rect rect = (Rect) o;
        return getX() == rect.getX() && getY() == rect.getY()
                && getWidth() == rect.getWidth() && getHeight() == rect.getHeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public String toString() {
        return "Rect{" +
                "location=" + location +
                ", dimension=" + dimension +
                '}';
    }

}
