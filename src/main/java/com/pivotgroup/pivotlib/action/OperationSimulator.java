package com.pivotgroup.pivotlib.action;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/16 21:24
 */

public interface OperationSimulator {

    /**
     * 使触点点击
     *
     * @param button     点击的键位
     * @param durationMs 按压持续时长
     */
    void click(int button, long durationMs);

    /**
     * 拖拽动作
     *
     * @param x          起点x
     * @param y          起点y
     * @param toX        目标x
     * @param toY        目标y
     * @param button     点击的键位
     * @param durationMs 移动的持续时长
     * @param steps      移动的段数
     */
    void drag(int x, int y, int toX, int toY, int button, long durationMs, int steps);

    /**
     * 从当前位置开始移动触点到目标
     *
     * @param toX        目标x
     * @param toY        目标y
     * @param durationMs 移动的持续时长
     * @param steps      移动的段数
     */
    void move(int toX, int toY, long durationMs, int steps);

}
