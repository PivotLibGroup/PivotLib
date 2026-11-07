package opencv;

import nu.pattern.OpenCV;
import org.junit.jupiter.api.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class FaceRecognition {

    static {
        OpenCV.loadLocally();
    }

    private static final String sourceImagePath = "src/test/resources/img.png";
    private static final String targetImagePath = "build/tx-faces.jpg";

    public static Mat loadImage(String path){
        return Imgcodecs.imread(path);
    }

    public static void saveImage(Mat matrix, String path){
        Imgcodecs.imwrite(path,matrix);
    }

    @Test
    public void example(){
        Mat loadedImage = loadImage(sourceImagePath);

        MatOfRect facesDetected = new MatOfRect();
        CascadeClassifier cascadeClassifier = new CascadeClassifier();
        int minFaceSize = Math.round(loadedImage.rows() * 0.1f);
        cascadeClassifier.load("./src/test/resources/haarcascades/haarcascade_frontalface_alt.xml");
        cascadeClassifier.detectMultiScale(loadedImage,
                facesDetected,
                1.1,
                3,
                Objdetect.CASCADE_SCALE_IMAGE,
                new Size(minFaceSize, minFaceSize),
                new Size()
        );

        Rect[] facesArray = facesDetected.toArray();
        for(Rect face : facesArray) {
            Imgproc.rectangle(loadedImage, face.tl(), face.br(), new Scalar(0, 0, 255), 3);
        }
        saveImage(loadedImage, targetImagePath);

    }


}
