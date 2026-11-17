package com.pivotgroup.pivotlib.ui;

import com.pivotgroup.pivotlib.model.Rect;

import java.util.Objects;

final class RoiSelection {

    private static final int MIN_SIZE = 40;

    private static final int DEFAULT_WIDTH = 400;

    private static final int DEFAULT_HEIGHT = 300;

    private final Rect bounds;

    private Rect roi;

    RoiSelection(Rect bounds, Rect initialRoi) {
        this.bounds = Objects.requireNonNull(bounds, "bounds");
        roi = initialRoi == null ? defaultRoi() : fit(initialRoi);
    }

    Rect getRoi() {
        return roi;
    }

    void setRoi(Rect roi) {
        this.roi = fit(Objects.requireNonNull(roi, "roi"));
    }

    void move(Rect original, int deltaX, int deltaY) {
        int x = (int) clamp(
                (long) original.getX() + deltaX,
                bounds.getX(),
                right(bounds) - original.getWidth());
        int y = (int) clamp(
                (long) original.getY() + deltaY,
                bounds.getY(),
                bottom(bounds) - original.getHeight());
        roi = Rect.of(x, y, original.getWidth(), original.getHeight());
    }

    void resize(Rect original, RoiHandle handle, int deltaX, int deltaY) {
        Objects.requireNonNull(handle, "handle");
        long left = original.getX();
        long right = right(original);
        long top = original.getY();
        long bottom = bottom(original);
        long minWidth = Math.min(MIN_SIZE, bounds.getWidth());
        long minHeight = Math.min(MIN_SIZE, bounds.getHeight());

        if (handle.isLeft()) {
            left = clamp(left + deltaX, bounds.getX(), right - minWidth);
        } else {
            right = clamp(right + deltaX, left + minWidth, right(bounds));
        }
        if (handle.isTop()) {
            top = clamp(top + deltaY, bounds.getY(), bottom - minHeight);
        } else {
            bottom = clamp(bottom + deltaY, top + minHeight, bottom(bounds));
        }

        roi = Rect.of((int) left, (int) top, (int) (right - left), (int) (bottom - top));
    }

    private Rect defaultRoi() {
        int width = Math.min(DEFAULT_WIDTH, bounds.getWidth());
        int height = Math.min(DEFAULT_HEIGHT, bounds.getHeight());
        int x = bounds.getX() + (bounds.getWidth() - width) / 2;
        int y = bounds.getY() + (bounds.getHeight() - height) / 2;
        return Rect.of(x, y, width, height);
    }

    private Rect fit(Rect candidate) {
        int width = (int) clamp(
                candidate.getWidth(),
                Math.min(MIN_SIZE, bounds.getWidth()),
                bounds.getWidth());
        int height = (int) clamp(
                candidate.getHeight(),
                Math.min(MIN_SIZE, bounds.getHeight()),
                bounds.getHeight());
        int x = (int) clamp(candidate.getX(), bounds.getX(), right(bounds) - width);
        int y = (int) clamp(candidate.getY(), bounds.getY(), bottom(bounds) - height);
        return Rect.of(x, y, width, height);
    }

    private static long right(Rect rect) {
        return (long) rect.getX() + rect.getWidth();
    }

    private static long bottom(Rect rect) {
        return (long) rect.getY() + rect.getHeight();
    }

    private static long clamp(long value, long min, long max) {
        return Math.max(min, Math.min(max, value));
    }
}
