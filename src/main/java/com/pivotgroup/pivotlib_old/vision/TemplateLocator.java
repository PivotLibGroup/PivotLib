package com.pivotgroup.pivotlib_old.vision;

import com.pivotgroup.pivotlib_old.capture.Capturer;
import com.pivotgroup.pivotlib_old.input.Locator;
import com.pivotgroup.pivotlib_old.util.MatMatchUtils;
import com.pivotgroup.pivotlib_old.util.MatUtils;
import com.pivotgroup.pivotlib_old.util.MaxLocResult;
import com.pivotgroup.pivotlib_old.util.coordinate.Loc;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

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

    /** scale 传 0 表示自动扫描尺度；传具体值表示只在该尺度上匹配。 */
    public static final double AUTO_SCALE = 0.0;

    private final Capturer screen;
    private final Mat template;
    private final double minScore;
    private final Rect region;
    private final double scale;

    public TemplateLocator(Capturer screen, Mat template) {
        this(screen, template, DEFAULT_MIN_SCORE);
    }

    public TemplateLocator(Capturer screen, Mat template, double minScore) {
        this(screen, template, minScore, null, AUTO_SCALE);
    }

    /** 只在指定区域里找，抓帧和匹配的成本都按面积下降。 */
    public TemplateLocator(Capturer screen, Mat template, double minScore, Rect region) {
        this(screen, template, minScore, region, AUTO_SCALE);
    }

    public TemplateLocator(Capturer screen, Mat template, double minScore, Rect region, double scale) {
        this.screen = Objects.requireNonNull(screen, "screen");
        this.template = Objects.requireNonNull(template, "template");
        if (this.template.empty()) {
            throw new IllegalArgumentException("template 为空，检查图片路径是否正确");
        }
        this.minScore = minScore;
        this.region = region;
        this.scale = scale;
    }

    @Override
    public Optional<Loc> find() {
        Rect r = region;
        Mat canvas = (r == null) ? screen.capture() : screen.capture(r);
        try {
            return locate(canvas, r);
        } finally {
            canvas.release();
        }
    }

    /**
     * 用调用方抓好的帧识别，不自己抓、也不释放这帧。
     * 一个 tick 里要判多个模板时走这条，能省掉重复抓帧（实测一次抓帧约 68ms）。
     */
    public Optional<Loc> find(Mat canvas) {
        Objects.requireNonNull(canvas, "canvas");
        Rect r = region;
        if (r == null) {
            return locate(canvas, null);
        }
        if (r.x < 0 || r.y < 0 || r.x + r.width > canvas.cols() || r.y + r.height > canvas.rows()) {
            throw new IllegalArgumentException("region 超出画布范围: " + this);
        }
        Mat view = new Mat(canvas, r);
        try {
            return locate(view, r);
        } finally {
            view.release();
        }
    }

    /**
     * 只判存在性：低于阈值一律 false，不会给出坐标。
     * 需要坐标时用 find()。
     */
    public boolean exists() {
        return find().isPresent();
    }

    /** 在调用方抓好的帧上判存在性，不自己抓、不释放这帧。 */
    public boolean exists(Mat canvas) {
        return find(canvas).isPresent();
    }

    private Optional<Loc> locate(Mat canvas, Rect offset) {
        MaxLocResult result = (scale > 0)
                ? MatMatchUtils.matchAtScale(canvas, template, scale)
                : MatMatchUtils.matchMultiScale(canvas, template, minScore);

        if (!(result.maxVal >= minScore)) {
            return Optional.empty();
        }

        Loc center = MatUtils.toCenter(result.maxLoc, template, result.scale);
        int dx = (offset == null) ? 0 : offset.x;
        int dy = (offset == null) ? 0 : offset.y;
        return Optional.of(new Loc(center.x + dx, center.y + dy));
    }

    @Override
    public void close() {
        template.release();
    }

    @Override
    public String toString() {
        String base = "template(" + template.cols() + "x" + template.rows() + ",>=" + minScore + ")";
        if (region != null) {
            base += " roi(" + region.x + "," + region.y + "," + region.width + "x" + region.height + ")";
        }
        if (scale > 0) {
            base += " scale(" + scale + ")";
        }
        return base;
    }
}
