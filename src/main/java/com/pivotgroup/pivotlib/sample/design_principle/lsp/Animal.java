package com.pivotgroup.pivotlib.sample.design_principle.lsp;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class Animal {

    private String name;

    private double runSpeed;

    public Animal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRunSpeed() {
        return runSpeed;
    }

    public void setRunSpeed(double runSpeed) {
        this.runSpeed = runSpeed;
    }
}
