package com.pivotgroup.pivotlib.model;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/15 22:18
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
    public String toString() {
        return "Dimension{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

}
