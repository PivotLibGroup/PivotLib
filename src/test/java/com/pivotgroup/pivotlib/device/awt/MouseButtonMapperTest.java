package com.pivotgroup.pivotlib.device.awt;

import com.pivotgroup.pivotlib.action.awt.MouseButtonMapper;
import org.junit.jupiter.api.Test;

import java.awt.event.InputEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests explicit mouse button mapping.
 *
 * @author xinliu
 */
class MouseButtonMapperTest {

    @Test
    void mapsSupportedButtons() {
        assertEquals(InputEvent.BUTTON1_DOWN_MASK, MouseButtonMapper.toAwtMask(1));
        assertEquals(InputEvent.BUTTON2_DOWN_MASK, MouseButtonMapper.toAwtMask(2));
        assertEquals(InputEvent.BUTTON3_DOWN_MASK, MouseButtonMapper.toAwtMask(3));
    }

    @Test
    void rejectsUnsupportedButton() {
        assertThrows(IllegalArgumentException.class, () -> MouseButtonMapper.toAwtMask(4));
    }
}
