package com.pivotgroup.pivotlib.actionsimulator;

public interface TouchPoint {
    void press();
    void move(int x,int y);
    void release();
}
