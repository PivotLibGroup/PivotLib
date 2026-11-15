package com.pivotgroup.pivotlib.model;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/15 22:18
 */

public class Dimension {

    private int width;

    private int height;

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Dimension{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

}
