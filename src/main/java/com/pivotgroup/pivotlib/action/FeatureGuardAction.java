package com.pivotgroup.pivotlib.action.routing;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.inspection.FeatureInspector;

import java.util.Objects;

public final class FeatureGuardAction implements Action {

    private final FeatureInspector inspector;

    private final Action onFeaturePresent;

    public FeatureGuardAction(
            FeatureInspector inspector,
            Action onFeaturePresent) {
        this.inspector = Objects.requireNonNull(inspector, "inspector");
        this.onFeaturePresent = Objects.requireNonNull(onFeaturePresent, "onFeaturePresent");
    }

    @Override
    public void apply(ActionContext context) {
        Objects.requireNonNull(context, "context");
        if (inspector.inspect().isPresent()) {
            onFeaturePresent.apply(context);
        }
    }
}
