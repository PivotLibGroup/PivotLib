package com.pivotgroup.pivotlib.device.robot;

import com.pivotgroup.pivotlib.capture.Capturer;
import com.pivotgroup.pivotlib.util.MatUtils;
import org.opencv.core.Mat;

import java.awt.Dimension;
import java.awt.Rectangle;
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
        BufferedImage image = robot.createScreenCapture(new Rectangle(screenSize));
        return MatUtils.bufferedImageToMat(image);
    }
}
