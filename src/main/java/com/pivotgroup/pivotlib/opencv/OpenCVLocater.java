package com.pivotgroup.pivotlib.opencv;

import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.vision.Locater;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.util.Optional;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class OpenCVLocater implements Locater {

    private Mat image;

    private Mat template;

    public OpenCVLocater(Mat image, Mat template) {
        this.image = image;
        this.template = template;
    }

    public static OpenCVLocater of(Mat image, Mat template) {
        return new OpenCVLocater(image, template);
    }

    @Override
    public Optional<Location> find() {
        Mat resultMat = new Mat();
        Imgproc.matchTemplate(image, template, resultMat, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult minMaxLocResult = Core.minMaxLoc(resultMat);
        double maxVal = minMaxLocResult.maxVal;
        if (maxVal >= 0.85) {
            Point maxLoc = minMaxLocResult.maxLoc;
            Location loc = new Location((int) maxLoc.x, (int) maxLoc.y);
            return Optional.of(loc);
        }
        return Optional.empty();
    }
}
