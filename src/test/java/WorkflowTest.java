import com.pivotgroup.pivotlib.capture.ScreenCapturer;
import com.pivotgroup.pivotlib.input.InputButton;
import com.pivotgroup.pivotlib.input.InputDevice;
import com.pivotgroup.pivotlib.input.Pointer;
import com.pivotgroup.pivotlib.input.gesture.Tap;
import com.pivotgroup.pivotlib.vision.TemplateLocator;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.ActionStep;
import nu.pattern.OpenCV;
import org.junit.jupiter.api.Test;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkflowTest {

    private static final int CANVAS_SIZE = 400;
    private static final int PATCH_X = 300;
    private static final int PATCH_Y = 200;
    private static final int TEMPLATE_SIZE = 16;
    private static final double MIN_SCORE = 0.95;

    static {
        OpenCV.loadLocally();
    }

    @Test
    public void tapLandsOnTemplateCenter() {
        Mat canvas = noise(CANVAS_SIZE, CANVAS_SIZE);
        Mat template = checkerboard(TEMPLATE_SIZE, 4);
        try {
            stamp(canvas, template, PATCH_X, PATCH_Y);

            RecordingDevice device = new RecordingDevice();
            try (TemplateLocator locator = new TemplateLocator(() -> canvas.clone(), template, MIN_SCORE)) {
                Workflow workflow = new Workflow("smoke", new Pointer(device),
                        new ScreenCapturer(() -> canvas.clone()));
                workflow.addStep(new ActionStep(new Tap(locator, InputButton.LEFT, 10)));

                assertTrue(workflow.exec());
            }
            assertEquals(List.of("move(308,208)", "press(LEFT)", "release(LEFT)"), device.log());
        } finally {
            canvas.release();
        }
    }

    @Test
    public void missDoesNothingAndFailsWorkflow() {
        Mat canvas = noise(CANVAS_SIZE, CANVAS_SIZE);
        Mat template = checkerboard(TEMPLATE_SIZE, 4);
        try {
            RecordingDevice device = new RecordingDevice();
            try (TemplateLocator locator = new TemplateLocator(() -> canvas.clone(), template, MIN_SCORE)) {
                Workflow workflow = new Workflow("miss", new Pointer(device),
                        new ScreenCapturer(() -> canvas.clone()));
                workflow.addStep(new ActionStep(new Tap(locator, InputButton.LEFT, 10)));

                assertFalse(workflow.exec());
                assertNotNull(workflow.getContext().failureMessage());
                assertTrue(device.log().isEmpty(), "识别失败时不应该产生任何输入事件");
            }
        } finally {
            canvas.release();
        }
    }

    private static Mat noise(int width, int height) {
        Mat mat = new Mat(height, width, CvType.CV_8UC1);
        byte[] pixels = new byte[width * height];
        new Random(42).nextBytes(pixels);
        mat.put(0, 0, pixels);
        return mat;
    }

    private static Mat checkerboard(int size, int cell) {
        Mat mat = new Mat(size, size, CvType.CV_8UC1);
        byte[] pixels = new byte[size * size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                pixels[y * size + x] = (byte) (((x / cell) + (y / cell)) % 2 == 0 ? 255 : 0);
            }
        }
        mat.put(0, 0, pixels);
        return mat;
    }

    private static void stamp(Mat canvas, Mat template, int x, int y) {
        Mat roi = canvas.submat(new Rect(x, y, template.cols(), template.rows()));
        try {
            template.copyTo(roi);
        } finally {
            roi.release();
        }
    }

    static class RecordingDevice implements InputDevice {

        private final List<String> events = new ArrayList<>();

        @Override
        public void move(int x, int y) {
            events.add("move(" + x + "," + y + ")");
        }

        @Override
        public void press(InputButton button) {
            events.add("press(" + button + ")");
        }

        @Override
        public void release(InputButton button) {
            events.add("release(" + button + ")");
        }

        @Override
        public void scroll(int clicks) {
            events.add("scroll(" + clicks + ")");
        }

        List<String> log() {
            return events;
        }
    }
}
