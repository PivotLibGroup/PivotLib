package com.pivotgroup.pivotlib.capture;

import org.opencv.core.Mat;

/**
 * 截图入口。实现类既可截全屏，也可只截某个窗口。
 * <p>
 * 约定：{@link #capture()} 每次返回一个新的 Mat，所有权交给调用方，由调用方 release。
 */
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
