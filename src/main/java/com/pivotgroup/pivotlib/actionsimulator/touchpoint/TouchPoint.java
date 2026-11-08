package com.pivotgroup.pivotlib.actionsimulator.touchpoint;

public interface TouchPoint {
    void press();
    void move(int x,int y);
    void release();
}
