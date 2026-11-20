package com.pivotgroup.pivotlib.sample.design_principle.lsp;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class FlyingAnimal extends Animal {

    private double flySpeed;

    public FlyingAnimal() {
    }

    public double getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(double flySpeed) {
        this.flySpeed = flySpeed;
    }
}
