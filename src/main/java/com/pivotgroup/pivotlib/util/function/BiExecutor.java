package com.pivotgroup.pivotlib.util.function;

@FunctionalInterface
public interface BiExecutor<T,U> {

    void exec(T t,U u);

}
