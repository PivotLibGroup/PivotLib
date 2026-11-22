package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.action.Action;
import com.pivotgroup.pivotlib.action.ActionContext;
import com.pivotgroup.pivotlib.action.TouchCapturer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class GestureSequence implements Action {

    private final TouchCapturer touchCapturer;

    private final List<Gesture> gestures = new ArrayList<>();

    public GestureSequence(TouchCapturer touchCapturer) {
        this.touchCapturer = touchCapturer;
    }

    public void addGesture(Gesture gesture) {
        gestures.add(gesture);
    }

    public void removeGesture(Gesture gesture) {
        gestures.remove(gesture);
    }

    @Override
    public void apply(ActionContext context) {
        for (Gesture gesture : gestures) {
            gesture.apply(touchCapturer);
        }
    }
}
