package com.pivotgroup.pivotlib_old.util;

import com.pivotgroup.pivotlib_old.util.coordinate.Loc;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.Optional;

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
        try {
            Imgproc.matchTemplate(canvas, template, scores, Imgproc.TM_CCOEFF_NORMED);
            Core.MinMaxLocResult best = Core.minMaxLoc(scores);
            return new MaxLocResult(best.maxVal,
                    new Loc((int) best.maxLoc.x, (int) best.maxLoc.y), 1.0);
        } finally {
            scores.release();
        }
    }

    /**
     * 带阈值门控的匹配：达不到 minScore 就不返回坐标。
     * 直接用 {@link #match(Mat, Mat)} 的返回值去点击是很危险的——目标不在画面里时它照样会给一个"最像"的位置。
     */
    public static Optional<MaxLocResult> match(Mat canvas, Mat template, double minScore) {
        MaxLocResult result = match(canvas, template);
        return result.maxVal >= minScore ? Optional.of(result) : Optional.empty();
    }

    /** 在指定尺度上匹配一次，结果里的 scale 是该尺度。尺度标定过之后走这条。 */
    public static MaxLocResult matchAtScale(Mat canvas, Mat template, double scale) {
        if (Math.abs(scale - 1.0) < 1e-9) {
            return match(canvas, template);
        }
        Mat scaled = resizeTemplate(template, scale);
        try {
            if (scaled.cols() > canvas.cols() || scaled.rows() > canvas.rows()) {
                throw new IllegalArgumentException("template 缩放后比画布还大: scale=" + scale);
            }
            MaxLocResult result = match(canvas, scaled);
            return new MaxLocResult(result.maxVal, result.maxLoc, scale);
        } finally {
            scaled.release();
        }
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
        return matchMultiScale(canvas, template, DEFAULT_MIN_SCORE);
    }

    /**
     * 先按原尺寸试探，达标即停；不达标再从 1.0 向两侧扩散。
     * 顺序很关键：真实的尺度偏差通常就在 0.8~1.25 之间，从 1.0 往外找比从 0.4 往上找命中得快得多。
     */
    public static MaxLocResult matchMultiScale(Mat canvas, Mat template, double minScore) {
        return matchMultiScale(canvas, template, minScore,
                DEFAULT_MIN_SCALE, DEFAULT_MAX_SCALE, DEFAULT_SCALE_STEP);
    }

    public static MaxLocResult matchMultiScale(Mat canvas, Mat template, double minScore,
                                               double minScale, double maxScale, double scaleStep) {
        if (minScale <= 1.0 + 1e-9 && 1.0 <= maxScale + 1e-9) {
            MaxLocResult best = match(canvas, template);
            if (best.maxVal >= minScore) {
                return best;
            }
            int steps = (int) Math.ceil(Math.max(1.0 - minScale, maxScale - 1.0) / scaleStep + 1e-9);
            for (int i = 1; i <= steps; i++) {
                for (double scale : new double[]{1.0 - i * scaleStep, 1.0 + i * scaleStep}) {
                    if (scale < minScale - 1e-9 || scale > maxScale + 1e-9) {
                        continue;
                    }
                    MaxLocResult candidate = tryAtScale(canvas, template, scale);
                    if (candidate == null) {
                        continue;
                    }
                    if (candidate.maxVal > best.maxVal) {
                        best = candidate;
                    }
                    if (best.maxVal >= minScore) {
                        return best;
                    }
                }
            }
            return best;
        }

        MaxLocResult best = null;
        for (double scale = minScale; scale <= maxScale + 1e-9; scale += scaleStep) {
            MaxLocResult candidate = tryAtScale(canvas, template, scale);
            if (candidate == null) {
                continue;
            }
            if (best == null || candidate.maxVal > best.maxVal) {
                best = candidate;
            }
            if (best.maxVal >= minScore) {
                return best;
            }
        }
        return (best == null) ? match(canvas, template) : best;
    }

    /** 模板缩放后超出画布时返回 null，不抛异常，让调用方继续试下一个尺度。 */
    private static MaxLocResult tryAtScale(Mat canvas, Mat template, double scale) {
        Mat scaled = resizeTemplate(template, scale);
        try {
            if (scaled.cols() > canvas.cols() || scaled.rows() > canvas.rows()) {
                return null;
            }
            MaxLocResult result = match(canvas, scaled);
            return new MaxLocResult(result.maxVal, result.maxLoc, scale);
        } finally {
            scaled.release();
        }
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
