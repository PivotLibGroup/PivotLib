package com.pivotgroup.pivotlib.screencapture;

import org.opencv.core.Mat;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 09:03
 * @description
 */

public class ScreenCapturer {

    private Capturer capturer;

    public ScreenCapturer(Capturer capturer) {
        this.capturer = capturer;
    }

    public Mat getScreen() {
        return capturer.capture();
    }

}
