package com.pivotgroup.pivotlib.infrastructure.vision;

import com.pivotgroup.pivotlib.domain.Location;
import com.pivotgroup.pivotlib.infrastructure.vision.opencv.OpenCVLocater;
import nu.pattern.OpenCV;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Optional;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class VisionTest {

    static {
        OpenCV.loadLocally();
    }

    @Test
    public void test() {
        Mat qqMat = Imgcodecs.imread("src/test/resources/qq.png");
        OpenCVLocater openCVLocater = OpenCVLocater.of(qqMat, qqMat);

        Optional<Location> location = openCVLocater.find();
        location.ifPresent(System.out::println);
    }

}
