package com.pivotgroup.pivotlib.action.infrastructure.awt;

import java.awt.event.InputEvent;

/**
 * Maps public button numbers to AWT button masks.
 *
 * @author xinliu
 */
final class MouseButtonMapper {

    private MouseButtonMapper() {
    }

    static int toAwtMask(int button) {
        return switch (button) {
            case 1 -> InputEvent.BUTTON1_DOWN_MASK;
            case 2 -> InputEvent.BUTTON2_DOWN_MASK;
            case 3 -> InputEvent.BUTTON3_DOWN_MASK;
            default -> throw new IllegalArgumentException("Unsupported mouse button: " + button);
        };
    }
}
