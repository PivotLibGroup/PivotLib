package com.pivotgroup.pivotlib_old.input;

public interface InputDevice {

    void move(int x, int y);

    void press(InputButton button);

    void release(InputButton button);

    void scroll(int clicks);

}
