import com.pivotgroup.pivotlib.util.Loc;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.util.MatUtils;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

/** 临时验证：多尺度匹配能否自动找回 QQ 图标，以及耗时多少，跑完即删 */
public class MultiScaleProbe {

    @Test
    public void verify() {
        Mat canvas = MatUtils.loadImage("screenshot.png");
        Mat wrongSize = MatUtils.loadImage("src/main/resources/qq.png");   // 58x59，尺寸不对

        long t0 = System.currentTimeMillis();
        Loc single = MatMatchUtils.match(canvas, wrongSize);
        long t1 = System.currentTimeMillis();
        System.out.println("单尺度    " + single + "    耗时 " + (t1 - t0) + " ms");

        long t2 = System.currentTimeMillis();
        Loc multi = MatMatchUtils.matchMultiScale(canvas, wrongSize);
        long t3 = System.currentTimeMillis();
        System.out.println("多尺度    " + multi + "    耗时 " + (t3 - t2) + " ms");

        // 同源模板（尺寸本来就对）：应该在第一次就命中并立刻返回
        Mat sameSize = new Mat(canvas, new Rect(1070, 1025, 38, 38)).clone();
        long t4 = System.currentTimeMillis();
        Loc fast = MatMatchUtils.matchMultiScale(canvas, sameSize);
        long t5 = System.currentTimeMillis();
        System.out.println("同源模板  " + fast + "    耗时 " + (t5 - t4) + " ms");

        sameSize.release();
        wrongSize.release();
        canvas.release();
    }
}
