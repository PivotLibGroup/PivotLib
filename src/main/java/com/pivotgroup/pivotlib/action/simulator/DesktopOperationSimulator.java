package com.pivotgroup.pivotlib.action.simulator;

import com.pivotgroup.pivotlib.action.OperationSimulator;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 17:33
 */

/**
 * 操作模拟器
 */
public class DesktopOperationSimulator implements OperationSimulator {

    private final TouchCapturable tc;

    public DesktopOperationSimulator(TouchCapturable touchCapturable) {
        this.tc = touchCapturable;
    }

    @Override
    public void click(int button, long durationMs) {
        tc.down(button);
        sleep(durationMs);
        tc.release(button);
    }

    @Override
    public void drag(int x, int y, int toX, int toY, int button, long durationMs, int steps) {
        try {
            tc.down(button);
            move(x, y, 0, 0);
            move(toX, toY, durationMs, steps);
        } finally {
            tc.release(button);
        }
    }

    @Override
    public void move(int toX, int toY, long durationMs, int steps) {
        Location loc = tc.getLocation();
        tc.move(loc.x, loc.y);
        int n = Math.max(1, steps);
        long t0 = System.nanoTime();
        for (int i = 1; i <= n; i++) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            long remain = durationMs * i / n - (System.nanoTime() - t0) / 1_000_000L;
            if (remain > 0) {
                sleep(remain);
            }
            int dx = toX - loc.x;
            int dy = toY - loc.y;
            int px = loc.x + (int) Math.round(dx * (double) i / n);
            int py = loc.y + (int) Math.round(dy * (double) i / n);
            tc.move(px, py);
        }
    }

    public TouchCapturable getTouchCapturer() {
        return tc;
    }

    /**
     * 等待时间
     *
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
