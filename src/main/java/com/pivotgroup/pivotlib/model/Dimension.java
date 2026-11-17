package com.pivotgroup.pivotlib.model;

import java.util.Objects;

/**
 * Immutable width and height.
 *
 * @author xinliu
 */
public class Dimension {

    private final int width;

    private final int height;

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Dimension of(int width, int height) {
        return new Dimension(width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dimension dimension = (Dimension) o;
        return width == dimension.width && height == dimension.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return "Dimension{" + "width=" + width + ", height=" + height + '}';
    }
}
