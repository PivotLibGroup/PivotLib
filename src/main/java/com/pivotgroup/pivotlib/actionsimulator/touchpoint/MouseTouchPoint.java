package com.pivotgroup.pivotlib.actionsimulator.touchpoint;

/**
 * 电脑端专用触点
 */
public interface MouseTouchPoint {

    void press(MouseButton button);

    void move(int x, int y);

    void release(MouseButton button);

    void scroll(int delta);
}
