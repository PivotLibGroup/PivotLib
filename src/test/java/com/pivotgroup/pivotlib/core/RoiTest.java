package com.pivotgroup.pivotlib.core;

import com.pivotgroup.pivotlib.action.gesture.CoordMode;
import com.pivotgroup.pivotlib.action.gesture.MoveGesture;
import com.pivotgroup.pivotlib.device.ImageCapturable;
import com.pivotgroup.pivotlib.device.RoiTouchCapturer;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;
import com.pivotgroup.pivotlib.roi.CoordinateSpace;
import nu.pattern.OpenCV;
import org.junit.jupiter.api.Test;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoiTest {

    static {
        OpenCV.loadLocally();
    }

    @Test
    void rectUsesHalfOpenBounds() {
        Rect roi = Rect.of(10, 20, 30, 40);

        assertTrue(roi.contains(10, 20));
        assertTrue(roi.contains(39, 59));
        assertFalse(roi.contains(40, 59));
        assertFalse(roi.contains(39, 60));
        assertThrows(IllegalArgumentException.class, () -> Rect.of(0, 0, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> Rect.of(0, 0, 10, -1));
    }

    @Test
    void pivotAllowsActionsInsideRoi() {
        FakeTouchCapturer touch = new FakeTouchCapturer(Location.of(10, 10));
        Pivot pivot = new Pivot(touch, new FakeImageCapturer(createCanvas()));
        pivot.setRoi(Rect.of(10, 10, 20, 20));
        pivot.addAction(context -> context.getTouchCapturer().move(29, 29));

        pivot.start();

        assertEquals(Location.of(29, 29), touch.getLocation());
        assertEquals(List.of(Location.of(29, 29)), touch.moves);
    }

    @Test
    void pivotRejectsActionOutsideRoiBeforeTouchingDevice() {
        FakeTouchCapturer touch = new FakeTouchCapturer(Location.of(10, 10));
        Pivot pivot = new Pivot(touch, new FakeImageCapturer(createCanvas()));
        pivot.setRoi(Rect.of(10, 10, 20, 20));
        pivot.addAction(context -> context.getTouchCapturer().move(30, 29));

        assertThrows(IllegalArgumentException.class, pivot::start);
        assertTrue(touch.moves.isEmpty());
    }

    @Test
    void roiTouchCapturerRejectsPressOutsideRoi() {
        FakeTouchCapturer touch = new FakeTouchCapturer(Location.of(0, 0));
        RoiTouchCapturer roiTouch = new RoiTouchCapturer(touch, Rect.of(10, 10, 20, 20));

        assertThrows(IllegalStateException.class, () -> roiTouch.down(1));
        assertEquals(0, touch.downCount);

        roiTouch.release(1);
        assertEquals(1, touch.releaseCount);
    }

    @Test
    void pivotWithoutRoiKeepsTouchUnrestricted() {
        FakeTouchCapturer touch = new FakeTouchCapturer(Location.of(0, 0));
        Pivot pivot = new Pivot(touch, new FakeImageCapturer(createCanvas()));
        pivot.addAction(context -> context.getTouchCapturer().move(-5, 100));

        pivot.start();

        assertEquals(Location.of(-5, 100), touch.getLocation());
    }

    @Test
    void locateWithinRoiReturnsAbsoluteLocation() {
        Mat canvas = createCanvas();
        Mat template = canvas.submat(5, 9, 7, 11).clone();
        FakeImageCapturer image = new FakeImageCapturer(canvas);
        Pivot pivot = new Pivot(new FakeTouchCapturer(Location.of(0, 0)), image);
        pivot.setRoi(Rect.of(100, 200, 30, 20));

        Location location = pivot.locate(template);

        assertEquals(Location.of(109, 207), location);
        assertFalse(image.fullCapture);
        assertEquals(List.of(100, 200, 30, 20), image.captureArguments);
        template.release();
        canvas.release();
    }

    @Test
    void roiRelativeSpaceConnectsIdentificationAndMovement() {
        Mat canvas = createCanvas();
        Mat template = canvas.submat(5, 9, 7, 11).clone();
        FakeTouchCapturer touch = new FakeTouchCapturer(Location.of(100, 200));
        FakeImageCapturer image = new FakeImageCapturer(canvas);
        Pivot pivot = new Pivot(touch, image);
        pivot.setRoi(Rect.of(100, 200, 30, 20));
        pivot.setCoordinateSpace(CoordinateSpace.ROI);
        pivot.addAction(new MoveGesture(
                () -> pivot.locate(template), CoordMode.ABSOLUTE, 0, 1));

        pivot.start();

        assertEquals(Location.of(109, 207), touch.getLocation());
        assertEquals(List.of(100, 200, 30, 20), image.captureArguments);
        template.release();
        canvas.release();
    }

    @Test
    void clearRoiRestoresFullCanvasIdentification() {
        Mat canvas = createCanvas();
        Mat template = canvas.submat(5, 9, 7, 11).clone();
        FakeImageCapturer image = new FakeImageCapturer(canvas);
        Pivot pivot = new Pivot(new FakeTouchCapturer(Location.of(0, 0)), image);
        pivot.setRoi(Rect.of(100, 200, 30, 20));
        pivot.clearRoi();

        Location location = pivot.locate(template);

        assertEquals(Location.of(9, 7), location);
        assertTrue(image.fullCapture);
        template.release();
        canvas.release();
    }

    @Test
    void locatesRegisteredTemplateByName() {
        Mat canvas = createCanvas();
        Mat template = canvas.submat(5, 9, 7, 11).clone();
        Pivot pivot = new Pivot(new FakeTouchCapturer(Location.of(0, 0)), new FakeImageCapturer(canvas));
        pivot.putIdentified("target", template);

        assertEquals(Location.of(9, 7), pivot.locate("target"));
        assertThrows(NoSuchElementException.class, () -> pivot.locate("missing"));
        template.release();
        canvas.release();
    }

    private static Mat createCanvas() {
        Mat canvas = new Mat(20, 30, CvType.CV_8UC1);
        byte[] data = new byte[20 * 30];
        Random random = new Random(42);
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) random.nextInt(256);
        }
        canvas.put(0, 0, data);
        return canvas;
    }

    private static final class FakeTouchCapturer implements TouchCapturable {

        private Location location;
        private final List<Location> moves = new ArrayList<>();
        private int downCount;
        private int releaseCount;

        private FakeTouchCapturer(Location location) {
            this.location = location;
        }

        @Override
        public void down(int button) {
            downCount++;
        }

        @Override
        public void release(int button) {
            releaseCount++;
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

    private static final class FakeImageCapturer implements ImageCapturable {

        private final Mat canvas;
        private boolean fullCapture;
        private List<Integer> captureArguments;

        private FakeImageCapturer(Mat canvas) {
            this.canvas = canvas;
        }

        @Override
        public Mat capture() {
            fullCapture = true;
            return canvas;
        }

        @Override
        public Mat capture(int x, int y, int width, int height) {
            captureArguments = List.of(x, y, width, height);
            return canvas;
        }
    }
}
