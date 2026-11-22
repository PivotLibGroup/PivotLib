package com.pivotgroup.pivotlib.action.gesture;

import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.action.TouchCapturer;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class MoveGesture implements Gesture {

    private final Location location;

    public MoveGesture(int x, int y) {
        this.location = new Location(x, y);
    }

    public MoveGesture(Location location) {
        this.location = location;
    }

    @Override
    public void apply(TouchCapturer touchCapturer) {
        touchCapturer.move(location.getX(), location.getY());
    }
}
