package com.pivotgroup.pivotlib.vision;

import com.pivotgroup.pivotlib.device.ImageCapturable;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.ResultLoc;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/14 22:39
 */

public class Identifier {

    private final ImageCapturable imageCapturer;

    public Identifier(ImageCapturable imageCapturer) {
        this.imageCapturer = imageCapturer;
    }

    public boolean isPresent(Mat templ) {
        return identify(templ).isPresent();
    }

    public ResultLoc identify(Mat templ) {
        Mat canvas = imageCapturer.capture();
        Mat scores = new Mat();
        Imgproc.matchTemplate(canvas, templ, scores, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult best = Core.minMaxLoc(scores);
        Point pLoc = best.maxLoc;
        return new ResultLoc(best.maxVal, offsetToCenter((int) pLoc.x, (int) pLoc.y, templ));
    }

    private Location offsetToCenter(int x, int y, Mat templ) {
        return new Location(x, y).offset(templ.cols() / 2, templ.rows() / 2);
    }

}
