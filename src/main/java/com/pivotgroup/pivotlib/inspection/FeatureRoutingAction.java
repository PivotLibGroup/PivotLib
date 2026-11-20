package com.pivotgroup.pivotlib.inspection;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.action.ActionContext;

import java.util.Map;
import java.util.Objects;

/**
 * Routes execution to the action associated with an inspected feature.
 *
 * @author xinliu
 */
public final class FeatureRoutingAction implements Action {

    private final FeatureInspector inspector;

    private final Map<Feature, Action> handlers;

    private final Action fallback;

    public FeatureRoutingAction(
            FeatureInspector inspector,
            Map<Feature, Action> handlers,
            Action fallback) {
        this.inspector = Objects.requireNonNull(inspector, "inspector");
        this.handlers = Map.copyOf(handlers);
        this.fallback = Objects.requireNonNull(fallback, "fallback");
    }

    @Override
    public void apply(ActionContext context) {
        Objects.requireNonNull(context, "context");
        Action selected = inspector.inspect()
                .map(handlers::get)
                .orElse(fallback);
        selected.apply(context);
    }
}
