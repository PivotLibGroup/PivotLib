package com.pivotgroup.pivotlib.actionsimulator;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 11:31
 * @description
 */

public interface ActionSimulator {

    void down();

    void move(int x, int y);

    void up();

    void tap(int x, int y, long duration);

    void swipe(int startX, int startY, int endX, int endY, long totalDuration, int steps);

    void sleep(long ms);

}
