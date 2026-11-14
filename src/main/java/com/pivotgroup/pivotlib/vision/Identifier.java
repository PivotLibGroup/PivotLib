package com.pivotgroup.pivotlib.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/14 22:39
 */

public class Identifier {

    public static final double DEFAULT_MIN_SCORE = 0.85;

    private final ImageCapturable imageCapturer;

    public Identifier(ImageCapturable imageCapturer) {
        this.imageCapturer = imageCapturer;
    }

    public boolean identify(Mat identifiedMat) {
        Mat canvas = imageCapturer.capture();
        Mat scores = new Mat();
        Imgproc.matchTemplate(canvas, identifiedMat, scores, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult best = Core.minMaxLoc(scores);
        return !(best.maxVal <= DEFAULT_MIN_SCORE);
    }

}
