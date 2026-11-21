package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.action.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for gestures without desktop side effects.
 *
 * @author xinliu
 */
class GestureTest {

    @Test
    void movesToAbsoluteLocation() {
        RecordingTouch touch = new RecordingTouch(10, 20);
        MoveGesture gesture = new MoveGesture(() -> Location.of(30, 40), CoordMode.ABSOLUTE, 0, 2);

        gesture.apply(new ActionContext(touch));

        assertEquals(List.of(Location.of(20, 30), Location.of(30, 40)), touch.moves);
        assertEquals(Location.of(30, 40), touch.location);
    }

    @Test
    void movesUsingRelativeCoordinates() {
        RecordingTouch touch = new RecordingTouch(100, 100);
        MoveGesture gesture = new MoveGesture(() -> Location.of(-50, 25), CoordMode.RELATIVE, 0, 1);

        gesture.apply(new ActionContext(touch));

        assertEquals(Location.of(50, 125), touch.location);
    }

    @Test
    void clickPressesAndReleasesButton() {
        RecordingTouch touch = new RecordingTouch(0, 0);
        ClickGesture gesture = new ClickGesture(() -> Location.of(10, 20), 1, 0);

        gesture.apply(new ActionContext(touch));

        assertEquals(List.of("move:10,20", "down:1", "release:1"), touch.events);
    }

    @Test
    void dragUsesLinearMotionAndReleasesButton() {
        RecordingTouch touch = new RecordingTouch(0, 0);
        DragGesture gesture = new DragGesture(
                () -> Location.of(10, 10), () -> Location.of(20, 20), 1, 0, 2);

        gesture.apply(new ActionContext(touch));

        assertEquals(Location.of(20, 20), touch.location);
        assertEquals("down:1", touch.events.get(1));
        assertEquals("release:1", touch.events.get(touch.events.size() - 1));
    }

    @Test
    void dragReleasesButtonWhenMovementFails() {
        RecordingTouch touch = new RecordingTouch(0, 0);
        touch.failingMove = 2;
        DragGesture gesture = new DragGesture(
                () -> Location.of(10, 10), () -> Location.of(20, 20), 1, 0, 2);

        assertThrows(IllegalStateException.class, () -> gesture.apply(new ActionContext(touch)));
        assertEquals("release:1", touch.events.get(touch.events.size() - 1));
    }

    @Test
    void rejectsNegativeDuration() {
        assertThrows(IllegalArgumentException.class,
                () -> new MoveGesture(() -> Location.of(0, 0), CoordMode.ABSOLUTE, -1, 1));
    }

    private static final class RecordingTouch implements TouchCapturable {

        private final List<Location> moves = new ArrayList<>();

        private final List<String> events = new ArrayList<>();

        private Location location;

        private int failingMove;

        private RecordingTouch(int x, int y) {
            location = Location.of(x, y);
        }

        @Override
        public void down(int button) {
            events.add("down:" + button);
        }

        @Override
        public void release(int button) {
            events.add("release:" + button);
        }

        @Override
        public void move(int x, int y) {
            if (failingMove > 0 && moves.size() + 1 == failingMove) {
                throw new IllegalStateException("Simulated movement failure");
            }
            location = Location.of(x, y);
            moves.add(location);
            events.add("move:" + x + ',' + y);
        }

        @Override
        public Location getLocation() {
            return location;
        }
    }
}
