package com.pivotgroup.pivotlib.vision;

import com.pivotgroup.pivotlib.core.Pivot;
import com.pivotgroup.pivotlib.device.ImageCapturable;
import com.pivotgroup.pivotlib.device.TouchCapturable;
import com.pivotgroup.pivotlib.action.routing.Feature;
import com.pivotgroup.pivotlib.action.routing.FeatureInspector;
import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.model.Rect;
import nu.pattern.OpenCV;
import org.junit.jupiter.api.Test;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests feature inspection against the current ROI.
 *
 * @author xinliu
 */
class OpenCvFeatureInspectorTest {

    static {
        OpenCV.loadLocally();
    }

    @Test
    void inspectsTemplatesInPriorityOrderWithOneRoiCapture() {
        Mat canvas = createCanvas();
        Mat loginTemplate = canvas.submat(5, 9, 7, 11).clone();
        Mat networkTemplate = canvas.submat(12, 16, 18, 22).clone();
        CapturingImage image = new CapturingImage(canvas);
        Pivot pivot = new Pivot(new NoOpTouch(), image);
        pivot.setRoi(Rect.of(100, 200, 30, 20));

        Map<Feature, Mat> templates = new LinkedHashMap<>();
        templates.put(TestFeature.NETWORK, networkTemplate);
        templates.put(TestFeature.LOGIN, loginTemplate);
        FeatureInspector inspector = pivot.createFeatureInspector(templates);

        assertEquals(Optional.of(TestFeature.NETWORK), inspector.inspect());
        assertEquals(1, image.captureCount);
        assertEquals(List.of(100, 200, 30, 20), image.captureArguments);
        loginTemplate.release();
        networkTemplate.release();
    }

    @Test
    void returnsEmptyWhenNoTemplatesAreConfigured() {
        Mat canvas = createCanvas();
        CapturingImage image = new CapturingImage(canvas);
        Pivot pivot = new Pivot(new NoOpTouch(), image);

        FeatureInspector inspector = pivot.createFeatureInspector(Map.of());

        assertEquals(Optional.empty(), inspector.inspect());
        assertEquals(1, image.captureCount);
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

    private enum TestFeature implements Feature {
        /** Login expired. */
        LOGIN,

        /** Network error. */
        NETWORK
    }

    private static final class CapturingImage implements ImageCapturable {

        private final Mat canvas;

        private final List<Integer> captureArguments = new ArrayList<>();

        private int captureCount;

        private CapturingImage(Mat canvas) {
            this.canvas = canvas;
        }

        @Override
        public Mat capture() {
            captureCount++;
            return canvas;
        }

        @Override
        public Mat capture(int x, int y, int width, int height) {
            captureCount++;
            captureArguments.addAll(List.of(x, y, width, height));
            return canvas;
        }
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
