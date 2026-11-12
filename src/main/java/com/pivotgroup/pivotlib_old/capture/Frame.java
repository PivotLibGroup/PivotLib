package com.pivotgroup.pivotlib_old.capture;

import org.opencv.core.Mat;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 一帧截图：像素 + 抓取时刻 + 序号。
 * 同一个 tick 里要判多个模板时抓一次共用，省掉重复抓帧。
 * 用完必须 close()，否则 native 内存不会回收。
 */
public final class Frame implements AutoCloseable {

    private static final AtomicLong SEQ = new AtomicLong();

    private final long id;
    private final long capturedAtMs;
    private final Mat mat;

    public Frame(Mat mat) {
        if (mat == null || mat.empty()) {
            throw new IllegalArgumentException("frame 为空，抓帧失败");
        }
        this.mat = mat;
        this.id = SEQ.incrementAndGet();
        this.capturedAtMs = System.currentTimeMillis();
    }

    public Mat mat() {
        return mat;
    }

    public long id() {
        return id;
    }

    public long capturedAtMs() {
        return capturedAtMs;
    }

    /** 距离抓帧时刻过了多少毫秒。 */
    public long ageMs() {
        return System.currentTimeMillis() - capturedAtMs;
    }

    /** 这帧是否旧于给定毫秒数，用来决定要不要重抓。 */
    public boolean olderThan(long maxAgeMs) {
        return ageMs() > maxAgeMs;
    }

    public int width() {
        return mat.cols();
    }

    public int height() {
        return mat.rows();
    }

    @Override
    public void close() {
        mat.release();
    }

    @Override
    public String toString() {
        return "Frame#" + id + "(" + width() + "x" + height() + ", age=" + ageMs() + "ms)";
    }
}
