package opencv.util;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

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

}
