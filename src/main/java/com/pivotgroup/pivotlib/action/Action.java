package com.pivotgroup.pivotlib.action;

/**
 * Executable automation action.
 *
 * @author xinliu
 */
@FunctionalInterface
public interface Action {

    /**
     * Executes this action.
     *
     * @param context action dependencies
     */
    void apply(ActionContext context);

}
