package com.pivotgroup.pivotlib.input;

public interface InputDevice {

    void move(int x, int y);

    void press(InputButton button);

    void release(InputButton button);

    void scroll(int clicks);
}
