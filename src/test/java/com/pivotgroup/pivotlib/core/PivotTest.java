package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.action.gesture.GestureSequence;
import com.pivotgroup.pivotlib.action.gesture.MoveGesture;
import com.pivotgroup.pivotlib.action.keyboard.CharKeyboard;
import com.pivotgroup.pivotlib.capturer.awt.AwtInputButton;
import com.pivotgroup.pivotlib.capturer.awt.AwtKeyboardCapturer;
import com.pivotgroup.pivotlib.capturer.awt.AwtTouchCapturer;
import org.junit.jupiter.api.Test;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class PivotTest {

    @Test
    public void test() {
        Pivot pivot = new Pivot();
        GestureSequence gestureSequence = new GestureSequence(new AwtTouchCapturer());
        gestureSequence.addGesture(new MoveGesture(700, 700));
        pivot.addAction(gestureSequence);
        pivot.addAction(new CharKeyboard(new AwtKeyboardCapturer(), 'a'));

        pivot.startActions();
    }
}
