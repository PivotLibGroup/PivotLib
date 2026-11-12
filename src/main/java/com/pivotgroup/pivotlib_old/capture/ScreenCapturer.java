package com.pivotgroup.pivotlib_old.capture;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class ScreenCapturer implements Capturer {

    private final Capturer capturer;

    public ScreenCapturer(Capturer capturer) {
        this.capturer = capturer;
    }

    @Override
    public Mat capture() {
        return capturer.capture();
    }

    @Override
    public Mat capture(Rect rect) {
        return capturer.capture(rect);
    }

    public Mat getScreen() {
        return capturer.capture();
    }
}
