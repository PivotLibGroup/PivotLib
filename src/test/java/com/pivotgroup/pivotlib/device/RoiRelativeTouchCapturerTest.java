package com.pivotgroup.pivotlib.device;

import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests ROI-relative coordinate conversion.
 *
 * @author xinliu
 */
class RoiRelativeTouchCapturerTest {

    @Test
    void convertsBetweenRelativeAndScreenCoordinates() {
        RecordingTouch touch = new RecordingTouch(Location.of(500, 500));
        RoiRelativeTouchCapturer relativeTouch = new RoiRelativeTouchCapturer(
                touch, Rect.of(500, 500, 500, 500));

        relativeTouch.move(20, 30);

        assertEquals(Location.of(520, 530), touch.location);
        assertEquals(Location.of(20, 30), relativeTouch.getLocation());
    }

    @Test
    void rejectsRelativeCoordinatesOutsideRoi() {
        RecordingTouch touch = new RecordingTouch(Location.of(500, 500));
        RoiRelativeTouchCapturer relativeTouch = new RoiRelativeTouchCapturer(
                touch, Rect.of(500, 500, 500, 500));

        assertThrows(IllegalArgumentException.class, () -> relativeTouch.move(500, 0));
        assertThrows(IllegalArgumentException.class, () -> relativeTouch.move(-1, 0));
        assertTrue(touch.moves.isEmpty());
    }

    private static final class RecordingTouch implements TouchCapturable {

        private final List<Location> moves = new ArrayList<>();

        private Location location;

        private RecordingTouch(Location location) {
            this.location = location;
        }

        @Override
        public void down(int button) {
        }

        @Override
        public void release(int button) {
        }

        @Override
        public void move(int x, int y) {
            location = Location.of(x, y);
            moves.add(location);
        }

        @Override
        public Location getLocation() {
            return location;
        }
    }
}
