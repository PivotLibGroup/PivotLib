package com.pivotgroup.pivotlib.capturer.awt;

import java.awt.event.InputEvent;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public enum AwtInputButton {

    LEFT(InputEvent.BUTTON1_MASK),
    MIDDLE(InputEvent.BUTTON2_MASK),
    RIGHT(InputEvent.BUTTON3_MASK);

    private final int button;

    AwtInputButton(int button) {
        this.button = button;
    }

    public int getButton() {
        return button;
    }
}
