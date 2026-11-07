package com.pivotgroup.pivotlib.util;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class MatUtils {

    // 官方 OpenCV 绑定需要先加载原生库；放在静态块里，
    // 任何用到本类的代码在第一次触碰时就会自动完成加载。
    static {
        nu.pattern.OpenCV.loadLocally();
    }

    public static Mat loadImage(String path){
        return Imgcodecs.imread(path);
    }

    public static void saveImage(Mat matrix, String path){
        Imgcodecs.imwrite(path,matrix);
    }

    /**
     * BufferedImage 转 OpenCV Mat(CV_8UC3, BGR)。
     * AWT 截图默认是 TYPE_INT_RGB，必须先画到 TYPE_3BYTE_BGR 上再取字节，
     * 否则通道 R/B 会颠倒。
     */
    public static Mat bufferedImageToMat(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage bgr = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = bgr.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        byte[] bytes = ((DataBufferByte) bgr.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(h, w, CvType.CV_8UC3);
        // put(row, col, byte[]) 把整块像素一次性写进去
        mat.put(0, 0, bytes);
        return mat;
    }
}
