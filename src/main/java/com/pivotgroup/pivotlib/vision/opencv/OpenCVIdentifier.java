package com.pivotgroup.pivotlib.vision.opencv;


import com.pivotgroup.pivotlib.model.Location;
import com.pivotgroup.pivotlib.vision.Identifier;
import com.pivotgroup.pivotlib.vision.Point;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Objects;
import java.util.Optional;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class OpenCVIdentifier implements Identifier {

    @Override
    public Optional<Location> identify(byte[] canvas, byte[] template) {
        Objects.requireNonNull(canvas,"canvas");
        Objects.requireNonNull(template,"template");

        Mat scores = new Mat();
        try {
            Imgproc.matchTemplate(MatData.dataToMat(canvas), MatData.dataToMat(template), scores, Imgproc.TM_CCOEFF_NORMED);
            Core.MinMaxLocResult best = Core.minMaxLoc(scores);
            org.opencv.core.Point mp = best.maxLoc;
            if (best.maxVal <= 0.85) {
                return Optional.empty();
            }
            return Optional.of(new Location((int) mp.x, (int) mp.y));
        } finally {
            scores.release();
        }
    }
}
