package com.pivotgroup.pivotlib_old.capture;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 09:05
 * @description
 */

public interface Capturer {

    Mat capture();

    /**
     * 区域抓帧。默认实现是"整屏抓完再取子图"，子图与原图共享内存，所以不省内存；
     * 有原生区域抓帧能力的实现类（如 RobotScreenCapturer）应覆盖它，才能省下抓帧时间。
     * 返回的 Mat 由调用方负责 release。
     */
    default Mat capture(Rect rect) {
        Mat full = capture();
        try {
            return new Mat(full, rect);
        } finally {
            full.release();
        }
    }

    /** 抓一帧并带上时间戳，交给 Frame 管理生命周期。 */
    default Frame grab() {
        return new Frame(capture());
    }

    default Frame grab(Rect rect) {
        return new Frame(capture(rect));
    }

}
