package com.pivotgroup.pivotlib.action.keyboard;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.action.KeyboardCapturer;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class CharKeyboard implements Action {

    private final KeyboardCapturer keyboardCapturer;

    private int keycode;

    public CharKeyboard(KeyboardCapturer keyboardCapturer, int keycode) {
        this.keyboardCapturer = keyboardCapturer;
        this.keycode = keycode;
    }

    @Override
    public void apply(ActionContext context) {
        keyboardCapturer.keyPress(keycode);
    }
}
