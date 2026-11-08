import java.awt.*;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 09:10
 * @description
 */

public class RobotSingleton {

    private static final Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static Robot getInstance() {
        return robot;
    }

}
