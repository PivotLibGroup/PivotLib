package com.pivotgroup.pivotlib.util;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 21:56
 * @description
 */

public class Pair <T,U>{

    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public U getSecond() {
        return second;
    }

    public void setSecond(U second) {
        this.second = second;
    }
}
