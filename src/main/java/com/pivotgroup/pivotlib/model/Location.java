package com.pivotgroup.pivotlib.model;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 18:35
 */

public class Location {

    public int x;
    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
