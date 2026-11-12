package com.pivotgroup.pivotlib_old.input;

import com.pivotgroup.pivotlib_old.util.coordinate.Loc;

public final class Pointer {

    private final InputDevice device;

    public Pointer(InputDevice device) {
        this.device = device;
    }

    public void move(Loc to) {
        device.move(to.x, to.y);
    }

    public void press(InputButton button) {
        device.press(button);
    }

    public void release(InputButton button) {
        device.release(button);
    }

    public void tap(Loc at, InputButton button, long pressMs) {
        move(at);
        press(button);
        try {
            sleep(pressMs);
        } finally {
            release(button);
        }
    }

    public void doubleTap(Loc at, InputButton button, long pressMs, long intervalMs) {
        tap(at, button, pressMs);
        sleep(intervalMs);
        tap(at, button, pressMs);
    }

    public void swipe(Loc from, Loc to, InputButton button, long totalMs, int steps) {
        int n = Math.max(1, steps);
        move(from);
        press(button);

        long t0 = System.nanoTime();
        try {
            for (int i = 1; i <= n; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                sleepUntil(t0, totalMs * i / n);
                move(lerp(from, to, i, n));
            }
        } finally {
            release(button);
        }
    }

    public void scroll(Loc at, int clicks) {
        move(at);
        device.scroll(clicks);
    }

    public void sleep(long ms) {
        if (ms <= 0) {
            return;
        }
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void sleepUntil(long t0, long offsetMs) {
        long remain = offsetMs - (System.nanoTime() - t0) / 1_000_000L;
        if (remain > 0) {
            try {
                Thread.sleep(remain);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static Loc lerp(Loc a, Loc b, int i, int n) {
        int dx = b.x - a.x;
        int dy = b.y - a.y;
        return new Loc(a.x + (int) Math.round(dx * (double) i / n),
                a.y + (int) Math.round(dy * (double) i / n));
    }
}
