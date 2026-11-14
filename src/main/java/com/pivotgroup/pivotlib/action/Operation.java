package com.pivotgroup.pivotlib.action;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/14 23:05
 */

public abstract class Operation implements Operable {

    private final String name;

    public Operation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
