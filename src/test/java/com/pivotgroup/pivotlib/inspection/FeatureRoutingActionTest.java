package com.pivotgroup.pivotlib.inspection;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests feature-based action routing.
 *
 * @author xinliu
 */
class FeatureRoutingActionTest {

    @Test
    void executesTheHandlerForTheInspectedFeature() {
        List<String> executions = new ArrayList<>();
        ActionContext context = new ActionContext(new RecordingTouch());
        FeatureRoutingAction action = new FeatureRoutingAction(
                () -> Optional.of(TestFeature.LOGIN),
                Map.of(TestFeature.LOGIN, recordingAction(executions, "login")),
                recordingAction(executions, "fallback"));

        action.apply(context);

        assertEquals(List.of("login"), executions);
    }

    @Test
    void executesFallbackWhenNoFeatureIsFound() {
        List<String> executions = new ArrayList<>();
        ActionContext context = new ActionContext(new RecordingTouch());
        FeatureRoutingAction action = new FeatureRoutingAction(
                Optional::empty,
                Map.of(TestFeature.LOGIN, recordingAction(executions, "login")),
                recordingAction(executions, "fallback"));

        action.apply(context);

        assertEquals(List.of("fallback"), executions);
    }

    @Test
    void executesFallbackWhenTheFeatureHasNoHandler() {
        List<String> executions = new ArrayList<>();
        ActionContext context = new ActionContext(new RecordingTouch());
        FeatureRoutingAction action = new FeatureRoutingAction(
                () -> Optional.of(TestFeature.NETWORK),
                Map.of(TestFeature.LOGIN, recordingAction(executions, "login")),
                recordingAction(executions, "fallback"));

        action.apply(context);

        assertEquals(List.of("fallback"), executions);
    }

    private static Action recordingAction(List<String> executions, String name) {
        return context -> executions.add(name);
    }

    private enum TestFeature implements Feature {
        /** Login expired. */
        LOGIN,

        /** Network error. */
        NETWORK
    }

    private static final class RecordingTouch implements TouchCapturable {

        @Override
        public void down(int button) {
        }

        @Override
        public void release(int button) {
        }

        @Override
        public void move(int x, int y) {
        }

        @Override
        public Location getLocation() {
            return Location.of(0, 0);
        }
    }
}
