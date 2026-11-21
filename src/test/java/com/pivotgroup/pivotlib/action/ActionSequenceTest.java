package com.pivotgroup.pivotlib.action;

import com.pivotgroup.pivotlib.model.Location;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests composite action execution.
 *
 * @author xinliu
 */
class ActionSequenceTest {

    @Test
    void executesActionsInInsertionOrder() {
        ActionSequence sequence = new ActionSequence();
        List<String> executions = new ArrayList<>();
        sequence.add(context -> executions.add("first"));
        sequence.add(context -> executions.add("second"));

        sequence.start(new ActionContext(new NoOpTouch()));

        assertEquals(List.of("first", "second"), executions);
    }

    @Test
    void removesAction() {
        ActionSequence sequence = new ActionSequence();
        Action action = context -> {
        };
        sequence.add(action);

        assertTrue(sequence.remove(action));
        sequence.start(new ActionContext(new NoOpTouch()));
    }

    private static final class NoOpTouch implements TouchCapturable {

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
