package com.pivotgroup.pivotlib_old.device.robot;

import com.pivotgroup.pivotlib_old.capture.Capturer;
import com.pivotgroup.pivotlib_old.util.MatUtils;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class RobotScreenCapturer implements Capturer {

    private final Robot robot;

    public RobotScreenCapturer() {
        this(RobotSingleton.getInstance());
    }

    public RobotScreenCapturer(Robot robot) {
        this.robot = robot;
    }

    @Override
    public Mat capture() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        BufferedImage image = robot.createScreenCapture(new java.awt.Rectangle(screenSize));
        return MatUtils.bufferedImageToMat(image);
    }

    @Override
    public Mat capture(Rect rect) {
        BufferedImage image = robot.createScreenCapture(
                new java.awt.Rectangle(rect.x, rect.y, rect.width, rect.height));
        return MatUtils.bufferedImageToMat(image);
    }
}
