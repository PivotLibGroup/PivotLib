package com.pivotgroup.pivotlib.vision;

import com.pivotgroup.pivotlib.device.ImageCapturable;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;
import com.pivotgroup.pivotlib.model.ResultLoc;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.util.Objects;

/**
 * Locates templates on captured images.
 *
 * @author xinliu
 */
public class Identifier {

    private static final double NOT_FOUND_SCORE = 0D;

    private final ImageCapturable imageCapturer;

    public Identifier(ImageCapturable imageCapturer) {
        this.imageCapturer = Objects.requireNonNull(imageCapturer, "imageCapturer");
    }

    public boolean isPresent(Mat templ) {
        return identify(templ).isPresent();
    }

    public ResultLoc identify(Mat templ) {
        Mat canvas = imageCapturer.capture();
        return identifyCaptured(templ, canvas, Location.of(0, 0));
    }

    public ResultLoc identify(Mat templ, Rect roi) {
        Objects.requireNonNull(roi, "roi");
        Mat canvas = imageCapturer.capture(roi.getX(), roi.getY(), roi.getWidth(), roi.getHeight());
        return identifyCaptured(templ, canvas, roi.getLocation());
    }

    private ResultLoc identifyCaptured(Mat templ, Mat canvas, Location origin) {
        Objects.requireNonNull(templ, "templ");
        Objects.requireNonNull(canvas, "canvas");
        try {
            if (canvas.empty() || templ.empty()
                    || templ.cols() > canvas.cols() || templ.rows() > canvas.rows()) {
                return new ResultLoc(NOT_FOUND_SCORE, origin);
            }
            return match(templ, canvas, origin);
        } finally {
            canvas.release();
        }
    }

    private ResultLoc match(Mat templ, Mat canvas, Location origin) {
        Mat scores = new Mat();
        try {
            Imgproc.matchTemplate(canvas, templ, scores, Imgproc.TM_CCOEFF_NORMED);
            Core.MinMaxLocResult best = Core.minMaxLoc(scores);
            Point match = best.maxLoc;
            Location location = Location.of((int) match.x, (int) match.y)
                    .offset(origin.x + templ.cols() / 2, origin.y + templ.rows() / 2);
            return new ResultLoc(best.maxVal, location);
        } finally {
            scores.release();
        }
    }
}
