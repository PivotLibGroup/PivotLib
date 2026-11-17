package com.pivotgroup.pivotlib.device.awt;

import com.pivotgroup.pivotlib.device.ImageCapturable;
import org.opencv.core.Mat;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * AWT Robot implementation of screen capture.
 *
 * @author xinliu
 */
public class RobotImageCapturer implements ImageCapturable {

    private final Robot robot;

    public RobotImageCapturer(Robot robot) {
        this.robot = Objects.requireNonNull(robot, "robot");
    }

    @Override
    public Mat capture() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return capture(0, 0, screenSize.width, screenSize.height);
    }

    @Override
    public Mat capture(int x, int y, int width, int height) {
        BufferedImage image = robot.createScreenCapture(new Rectangle(x, y, width, height));
        return BufferedImageMatConverter.toMat(image);
    }
}
