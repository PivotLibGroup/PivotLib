package com.pivotgroup.pivotlib.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Composite action that executes its children in insertion order.
 *
 * @author xinliu
 */
public final class ActionSequence implements Action{
    private final List<Action> actions = new CopyOnWriteArrayList<>();

    @Override
    public void apply(ActionContext context) {
        Objects.requireNonNull(context, "context");
        for (Action action : actions) {
            action.apply(context);
        }
    }

    public void add(Action action) {
        actions.add(Objects.requireNonNull(action, "action"));
    }

    public boolean remove(Action action) {
        return actions.remove(action);
    }

}
