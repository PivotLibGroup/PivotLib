package com.pivotgroup.pivotlib.util;

import nu.pattern.OpenCV;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class MatUtils {

    static {
        OpenCV.loadLocally();
    }

    public static Mat loadImage(String path){
        return Imgcodecs.imread(path);
    }

    public static void saveImage(Mat matrix, String path){
        Imgcodecs.imwrite(path,matrix);
    }

    public static Mat bufferedImageToMat(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage bgr = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = bgr.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        byte[] bytes = ((DataBufferByte) bgr.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(h, w, CvType.CV_8UC3);
        mat.put(0, 0, bytes);
        return mat;
    }
}
