package com.pivotgroup.pivotlib.input;

public interface InputDevice {

    void move(int x, int y);

    void press(InputButton button);

    void release(InputButton button);

    /**
     * @param clicks 正数向上/远离用户（与 AWT Robot.mouseWheel 方向一致）
     */
    void scroll(int clicks);
}
