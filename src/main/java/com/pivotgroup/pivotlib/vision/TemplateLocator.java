package com.pivotgroup.pivotlib.vision;

import com.pivotgroup.pivotlib.capture.Capturer;
import com.pivotgroup.pivotlib.input.Locator;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.util.MatUtils;
import com.pivotgroup.pivotlib.util.MaxLocResult;
import com.pivotgroup.pivotlib.util.coordinate.Loc;
import org.opencv.core.Mat;

import java.util.Objects;
import java.util.Optional;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/9 20:42
 * @description 模板定位器
 */

public final class TemplateLocator implements Locator, AutoCloseable {

    public static final double DEFAULT_MIN_SCORE = 0.85;

    private final Capturer screen;
    private final Mat template;
    private final double minScore;

    public TemplateLocator(Capturer screen, Mat template) {
        this(screen, template, DEFAULT_MIN_SCORE);
    }

    public TemplateLocator(Capturer screen, Mat template, double minScore) {
        this.screen = Objects.requireNonNull(screen, "screen");
        this.template = Objects.requireNonNull(template, "template");
        if (this.template.empty()) {
            throw new IllegalArgumentException("template 为空，检查图片路径是否正确");
        }
        this.minScore = minScore;
    }

    @Override
    public Optional<Loc> find() {
        Mat canvas = screen.capture();
        try {
  /*          MaxLocResult result = MatMatchUtils.matchMultiScale(canvas, template, minScore,
                    MatMatchUtils.DEFAULT_MIN_SCALE,
                    MatMatchUtils.DEFAULT_MAX_SCALE,
                    MatMatchUtils.DEFAULT_SCALE_STEP);*/

            MaxLocResult result = MatMatchUtils.matchMultiScale(canvas, template, minScore);

            if (!(result.maxVal >= minScore)) {
                return Optional.empty();
            }
            return Optional.of(MatUtils.toCenter(result.maxLoc, template, result.scale));
        } finally {
            canvas.release();
        }
    }

    @Override
    public void close() {
        template.release();
    }

    @Override
    public String toString() {
        return "template(" + template.cols() + "x" + template.rows() + ",>=" + minScore + ")";
    }
}
