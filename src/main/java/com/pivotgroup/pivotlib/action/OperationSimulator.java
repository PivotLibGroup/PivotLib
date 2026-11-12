package com.pivotgroup.pivotlib.action;

import com.pivotgroup.pivotlib.model.Location;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 17:33
 */

/**
 * 操作模拟器
 */
public class OperationSimulator {

    private TouchCapturer tc;

    public OperationSimulator(TouchCapturer touchCapturer) {
        this.tc = touchCapturer;
    }

    /**
     * 使触点点击
     * @param button 点击的键位
     * @param durationMs 按压持续时长
     */
    public void click(int button, long durationMs) {
        tc.down(button);
        sleep(durationMs);
        tc.release(button);
    }

    /**
     * 拖拽动作
     * @param x 起点x
     * @param y 起点y
     * @param toX 目标x
     * @param toY 目标y
     * @param button 点击的键位
     * @param durationMs 移动的持续时长
     * @param steps 移动的段数
     */
    public void drag(int x, int y, int toX, int toY, int button, long durationMs, int steps) {
        try {
            tc.down(button);
            move(x, y, toX, toY, durationMs, steps);
        } finally {
            tc.release(button);
        }
    }

    /**
     * 从当前位置开始移动触点
     * @param toX 目标x
     * @param toY 目标y
     * @param durationMs 移动的持续时长
     * @param steps 移动的段数
     */
    public void move(int toX, int toY, long durationMs, int steps) {
        Location loc = tc.getLocation();
        move(loc.x, loc.y, toX, toY, durationMs, steps);
    }

    /**
     * 从指定位置开始移动触点
     * @param x
     * @param y
     * @param toX
     * @param toY
     * @param durationMs
     * @param steps
     */
    public void move(int x, int y, int toX, int toY, long durationMs, int steps) {
        int n = Math.max(1, steps);
        moveDirectly(x, y);
        long t0 = System.nanoTime();
        for (int i = 1; i <= n; i++) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            long remain = durationMs * i / n - (System.nanoTime() - t0) / 1_000_000L;
            if (remain > 0) {
                sleep(remain);
            }
            int dx = toX - x;
            int dy = toY - y;
            int px = x + (int) Math.round(dx * (double) i / n);
            int py = y + (int) Math.round(dy * (double) i / n);
            moveDirectly(px, py);
        }
    }

    /**
     * 直接移动触点到指定位置
     * @param x
     * @param y
     */
    public void moveDirectly(int x, int y) {
        tc.move(x, y);
    }

    /**
     * 等待时间
     * @param durationMs
     */
    private void sleep(long durationMs) {
        try {
            Thread.sleep(durationMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
