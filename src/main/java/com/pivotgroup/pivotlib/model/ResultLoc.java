package com.pivotgroup.pivotlib.model;

import java.util.function.Consumer;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/15 12:41
 */

public class ResultLoc {

    public static final double DEFAULT_MIN_SCORE = 0.85;

    public final double maxVal;
    public final Location loc;

    public ResultLoc(double maxVal, Location loc) {
        this.maxVal = maxVal;
        this.loc = loc;
    }

    public void computeIfPresent(Consumer<Location> consumer) {
        computeIfPresent(DEFAULT_MIN_SCORE, consumer);
    }

    public void computeIfPresent(final double minScore, Consumer<Location> consumer) {
        if (isPresent(minScore)) {
            consumer.accept(loc);
        }
    }

    public boolean isPresent() {
        return isPresent(DEFAULT_MIN_SCORE);
    }

    public boolean isPresent(double minScore) {
        return !(maxVal <= minScore);
    }

    @Override
    public String toString() {
        return "ResultLoc{" +
                "maxVal=" + maxVal +
                ", loc=" + loc +
                '}';
    }
}
