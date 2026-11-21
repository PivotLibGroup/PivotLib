package com.pivotgroup.pivotlib.vision;

import com.pivotgroup.pivotlib.device.ImageCapturable;
import com.pivotgroup.pivotlib.model.Rect;
import com.pivotgroup.pivotlib.model.ResultLoc;
import nu.pattern.OpenCV;
import org.junit.jupiter.api.Test;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests template matching boundaries and resource ownership.
 *
 * @author xinliu
 */
class IdentifierTest {

    static {
        OpenCV.loadLocally();
    }

    @Test
    void returnsNoMatchAndReleasesCanvasForOversizedTemplate() {
        TrackingMat canvas = new TrackingMat(10);
        Mat template = Mat.ones(20, 20, CvType.CV_8UC3);
        OpenCVIdentifier identifier = new OpenCVIdentifier(new FixedImage(canvas));

        ResultLoc result = identifier.identify(template, Rect.of(5, 6, 10, 10));

        assertFalse(result.isPresent());
        assertEquals(5, result.loc.x);
        assertEquals(6, result.loc.y);
        assertTrue(canvas.released);
        template.release();
    }

    private static final class TrackingMat extends Mat {

        private boolean released;

        private TrackingMat(int size) {
            super(size, size, CvType.CV_8UC3);
        }

        @Override
        public void release() {
            released = true;
            super.release();
        }
    }

    private static final class FixedImage implements ImageCapturable {

        private final Mat canvas;

        private FixedImage(Mat canvas) {
            this.canvas = canvas;
        }

        @Override
        public Mat capture() {
            return canvas;
        }

        @Override
        public Mat capture(int x, int y, int width, int height) {
            return canvas;
        }
    }
}
