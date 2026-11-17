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
public final class ActionSequence implements Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionSequence.class);

    private final List<Action> actions = new CopyOnWriteArrayList<>();

    public void add(Action action) {
        actions.add(Objects.requireNonNull(action, "action"));
    }

    public boolean remove(Action action) {
        return actions.remove(action);
    }

    @Override
    public void apply(ActionContext context) {
        Objects.requireNonNull(context, "context");
        for (Action action : actions) {
            long startedAt = System.nanoTime();
            action.apply(context);
            long elapsedMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startedAt);
            LOGGER.info("\"{}\"操作执行完成,耗时:{}ms", action.getClass().getSimpleName(), elapsedMillis);
        }
    }
}
