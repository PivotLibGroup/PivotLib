package com.pivotgroup.pivotlib.roi;

import com.pivotgroup.pivotlib.device.RoiTouchCapturer;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests ROI state and device decoration.
 *
 * @author xinliu
 */
class RoiStateTest {

    @Test
    void decoratesOnlyWhenRoiIsSet() {
        RoiState state = new RoiState();
        TouchCapturable touch = new NoOpTouch();

        assertSame(touch, state.decorate(touch));
        state.set(Rect.of(10, 20, 30, 40));
        assertInstanceOf(RoiTouchCapturer.class, state.decorate(touch));
        assertEquals(Rect.of(10, 20, 30, 40), state.get().orElseThrow());

        state.clear();
        assertTrue(state.get().isEmpty());
        assertSame(touch, state.decorate(touch));
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
