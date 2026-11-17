package com.pivotgroup.pivotlib.device.awt;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Objects;

/**
 * Converts AWT images to OpenCV matrices.
 *
 * @author xinliu
 */
final class BufferedImageMatConverter {

    private BufferedImageMatConverter() {
    }

    static Mat toMat(BufferedImage image) {
        Objects.requireNonNull(image, "image");
        BufferedImage bgrImage = toBgr(image);
        byte[] data = ((DataBufferByte) bgrImage.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(bgrImage.getHeight(), bgrImage.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }

    private static BufferedImage toBgr(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            return image;
        }
        BufferedImage bgr = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = bgr.createGraphics();
        try {
            graphics.setComposite(AlphaComposite.Src);
            graphics.drawImage(image, 0, 0, null);
        } finally {
            graphics.dispose();
        }
        return bgr;
    }
}
