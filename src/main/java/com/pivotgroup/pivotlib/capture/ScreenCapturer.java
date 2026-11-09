package com.pivotgroup.pivotlib.capture;

import org.opencv.core.Mat;

public class ScreenCapturer implements Capturer {

    private final Capturer capturer;

    public ScreenCapturer(Capturer capturer) {
        this.capturer = capturer;
    }

    @Override
    public Mat capture() {
        return capturer.capture();
    }

    public Mat getScreen() {
        return capturer.capture();
    }
}
