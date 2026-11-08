package com.pivotgroup.pivotlib.util;

import org.opencv.core.Mat;

public enum Anchor {
    TOP_LEFT, TOP_CENTER, TOP_RIGHT,
    MIDDLE_LEFT, CENTER, MIDDLE_RIGHT,
    BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT;

    public static Loc anchorDelta(Anchor anchor, int x, int y, Mat tmpl) {
        switch (anchor) {
            case TOP_LEFT:
                break;
            case TOP_CENTER:
                x += tmpl.cols() / 2;
                break;
            case TOP_RIGHT:
                x += tmpl.cols();
                break;
            case MIDDLE_LEFT:
                y += tmpl.rows() / 2;
                break;
            case CENTER:
                x += tmpl.cols() / 2;
                y += tmpl.rows() / 2;
                break;
            case MIDDLE_RIGHT:
                x += tmpl.cols();
                y += tmpl.rows() / 2;
                break;
            case BOTTOM_LEFT:
                y += tmpl.rows();
                break;
            case BOTTOM_CENTER:
                x += tmpl.cols() / 2;
                y += tmpl.rows();
                break;
            case BOTTOM_RIGHT:
                x += tmpl.cols();
                y += tmpl.rows();
                break;
        }
        return new Loc(0, x, y);
    }
}
