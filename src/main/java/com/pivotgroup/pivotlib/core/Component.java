package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.model.Dimension;
import com.pivotgroup.pivotlib.model.Location;

/**
 * Base node of the workspace composite hierarchy.
 *
 * @author xinliu
 */
@SuppressWarnings("PMD.AbstractClassShouldStartWithAbstractNamingRule")
public abstract class Component {

    private int x;

    private int y;

    private int width;

    private int height;

    private Component parent;

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

    void setParent(Component parent) {
        this.parent = parent;
    }
}
