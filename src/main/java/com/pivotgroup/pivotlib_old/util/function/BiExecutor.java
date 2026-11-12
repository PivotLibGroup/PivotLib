package com.pivotgroup.pivotlib_old.util.function;

@FunctionalInterface
public interface BiExecutor<T,U> {

    void exec(T t,U u);

}
