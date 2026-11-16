package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.model.Dimension;
import com.pivotgroup.pivotlib.model.Location;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/16 19:46
 */

public abstract class Component {

    int x;

    int y;

    int width;

    int height;

    Component parent;

    public Component() {

    }

    public Component getParent() {
        return parent;
    }

    public Location getLocation() {
        return Location.of(x, y);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Dimension getSize() {
        return Dimension.of(width, height);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

}
