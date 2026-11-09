package com.pivotgroup.pivotlib.util;

import com.pivotgroup.pivotlib.util.coordinate.Loc;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class MatMatchUtils {

    public static final double DEFAULT_MIN_SCORE = 0.85;
    public static final double DEFAULT_MIN_SCALE = 0.40;
    public static final double DEFAULT_MAX_SCALE = 1.60;
    public static final double DEFAULT_SCALE_STEP = 0.10;

    public static MaxLocResult match(Mat canvas, Mat template) {
        if (canvas == null || canvas.empty()) {
            throw new IllegalArgumentException("canvas is null or empty.");
        }
        if (template == null || template.empty()) {
            throw new IllegalArgumentException("template is null or empty.");
        }
        if (template.cols() > canvas.cols() || template.rows() > canvas.rows()) {
            throw new IllegalArgumentException("The template is larger than the canvas and cannot be matched.");
        }

        Mat scores = new Mat();
        Imgproc.matchTemplate(canvas, template, scores, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult best = Core.minMaxLoc(scores);
        scores.release();

        return new MaxLocResult(best.maxVal, new Loc((int) best.maxLoc.x, (int) best.maxLoc.y));
    }

    public static MaxLocResult match(String canvasPath, String templatePath) {
        Mat canvas = MatUtils.loadImage(canvasPath);
        Mat template = MatUtils.loadImage(templatePath);
        try {
            return match(canvas, template);
        } finally {
            canvas.release();
            template.release();
        }
    }

    public static MaxLocResult matchMultiScale(Mat canvas, Mat template) {
        return matchMultiScale(canvas, template,
                DEFAULT_MIN_SCORE, DEFAULT_MIN_SCALE, DEFAULT_MAX_SCALE, DEFAULT_SCALE_STEP);
    }

    public static MaxLocResult matchMultiScale(Mat canvas, Mat template, double minScore,
                                      double minScale, double maxScale, double scaleStep) {
        MaxLocResult best = match(canvas, template);
        if (best.maxVal >= minScore) {
            return best;
        }

        for (double scale = minScale; scale <= maxScale + 1e-9; scale += scaleStep) {
            if (Math.abs(scale - 1.0) < 1e-9) {
                continue;
            }
            Mat scaled = resizeTemplate(template, scale);
            if (scaled.cols() > canvas.cols() || scaled.rows() > canvas.rows()) {
                scaled.release();
                continue;
            }
            try {
                MaxLocResult loc = match(canvas, scaled);
                if (loc.maxVal > best.maxVal) {
                    best = loc;
                }
                if (best.maxVal >= minScore) {
                    break;
                }
            } finally {
                scaled.release();
            }
        }
        return best;
    }

    public static MaxLocResult matchMultiScale(String canvasPath, String templatePath) {
        Mat canvas = MatUtils.loadImage(canvasPath);
        Mat template = MatUtils.loadImage(templatePath);
        try {
            return matchMultiScale(canvas, template);
        } finally {
            canvas.release();
            template.release();
        }
    }

    private static Mat resizeTemplate(Mat template, double scale) {
        Mat scaled = new Mat();
        int width = Math.max(1, (int) Math.round(template.cols() * scale));
        int height = Math.max(1, (int) Math.round(template.rows() * scale));
        Imgproc.resize(template, scaled, new Size(width, height), 0, 0,
                scale < 1.0 ? Imgproc.INTER_AREA : Imgproc.INTER_LINEAR);
        return scaled;
    }
}
