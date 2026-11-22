package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.TouchCapturer;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class DownGesture implements Gesture {

    private final int button;

    public DownGesture(int button) {
        this.button = button;
    }

    @Override
    public void apply(TouchCapturer touchCapturer) {
        touchCapturer.down(button);
    }

}
