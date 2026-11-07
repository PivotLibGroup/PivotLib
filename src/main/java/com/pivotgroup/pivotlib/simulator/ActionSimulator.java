package com.pivotgroup.pivotlib.simulator;

import com.pivotgroup.pivotlib.util.function.BiExecutor;
import com.pivotgroup.pivotlib.util.function.Executor;

/**
 * 动作模拟器
 */
public class ActionSimulator {

    private Executor downExecutor = () -> {
    };
    private Executor upExecutor = () -> {
    };
    private BiExecutor<Integer, Integer> moveExecutor = (x, y) -> {
    };

    /**
     * 按下触点（保持按下状态）
     */
    public void down() {
        downExecutor.exec();
    }

    /**
     * 移动触点到指定坐标
     */
    public void move(int x, int y) {
        moveExecutor.exec(x, y);
    }

    /**
     * 抬起触点
     */
    public void up() {
        upExecutor.exec();
    }

    /**
     * 轻击指定坐标位置
     * @param x        点击坐标x
     * @param y        点击坐标y
     * @param duration 按下时长(ms)
     */
    public void tap(int x, int y, long duration) {
        move(x, y);
        down();
        sleep(duration);
        up();
    }

    /**
     * 指定坐标拖拽
     * @param startX        起点x
     * @param startY        起点y
     * @param endX          终点x
     * @param endY          终点y
     * @param totalDuration 总时长(ms)
     * @param steps         移动步数，越多越平滑
     */
    public void swipe(int startX, int startY, int endX, int endY, long totalDuration, int steps) {
        if (steps <= 0) steps = 1;
        long stepDelay = totalDuration / steps;

        move(startX, startY);
        down();

        for (int i = 1; i <= steps; i++) {
            int x = startX + (endX - startX) * i / steps;
            int y = startY + (endY - startY) * i / steps;
            sleep(stepDelay);
            move(x, y);
        }

        up();
    }


    public void mountDown(Executor downExecutor) {
        this.downExecutor = downExecutor;
    }

    public void mountMove(BiExecutor<Integer, Integer> moveExecutor) {
        this.moveExecutor = moveExecutor;
    }

    public void mountUp(Executor upExecutor) {
        this.upExecutor = upExecutor;
    }

    public void sleep(long ms) {
        if (ms <= 0) return;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
