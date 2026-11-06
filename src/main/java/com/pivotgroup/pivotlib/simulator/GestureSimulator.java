package com.pivotgroup.pivotlib.simulator;

/**
 * 插个眼，可能 Action 做后缀更合适
 */
public class GestureSimulator implements Simulator {

    private int startX, startY;
    private int endX, endY;
    private int speed;

    private int currentX, currentY;

    public GestureSimulator(int startX, int startY, int endX, int endY, int speed) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.speed = speed;
    }

    @Override
    public void exec() {
        while (true) {


            if (currentX == endX && currentY == endY)
                break;
        }
    }
}
