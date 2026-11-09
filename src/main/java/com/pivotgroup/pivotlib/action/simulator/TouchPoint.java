package com.pivotgroup.pivotlib.action.simulator;


public interface TouchPoint {

    void press(TouchButton button);

    void move(int x, int y);

    void release(TouchButton button);

    void scroll(int delta);

}
