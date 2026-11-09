package com.pivotgroup.example;

import com.pivotgroup.pivotlib.capture.ScreenCapturer;
import com.pivotgroup.pivotlib.device.robot.RobotInputDevice;
import com.pivotgroup.pivotlib.device.robot.RobotScreenCapturer;
import com.pivotgroup.pivotlib.input.InputButton;
import com.pivotgroup.pivotlib.input.Locator;
import com.pivotgroup.pivotlib.input.Pointer;
import com.pivotgroup.pivotlib.input.gesture.Swipe;
import com.pivotgroup.pivotlib.input.gesture.Tap;
import com.pivotgroup.pivotlib.util.MatUtils;
import com.pivotgroup.pivotlib.vision.TemplateLocator;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.ActionStep;
import com.pivotgroup.pivotlib.workflow.step.WaitStep;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用案例：造梦西游4 每日签到。
 * 模板作为 classpath 资源放在 src/main/resources/tpl/ 下，换成自己的截图即可。
 */
public class DailySignInExample {

    private static final String[] TEMPLATES = {"开始游戏.png", "背包.png", "装备第一格.png", "关闭.png"};

    public static void main(String[] args) {

        if (!templatesReady()) {
            return;
        }

        // 全脚本只有这两行与平台有关，换模拟器抓窗/真机只改这里
        ScreenCapturer screen = new ScreenCapturer(new RobotScreenCapturer());
        Pointer pointer = new Pointer(new RobotInputDevice());

        // 模板只加载一次，整段脚本复用；try-with-resources 统一释放 template
        try (TemplateLocator startBtn = new TemplateLocator(screen, load("开始游戏.png"));
             TemplateLocator bagIcon = new TemplateLocator(screen, load("背包.png"), 0.90);
             TemplateLocator itemTpl = new TemplateLocator(screen, load("装备第一格.png"));
             TemplateLocator closeBtn = new TemplateLocator(screen, load("关闭.png"))) {

            Workflow daily = new Workflow("每日签到", pointer, screen);

            // 1. 等界面加载：3 秒内每 200ms 找一次，找到才点
            daily.addStep(new ActionStep(new Tap(
                    Locator.retry(startBtn, 3000, 200), InputButton.LEFT, 80)));

            // 2. 进主城
            daily.addStep(new WaitStep(1500));

            // 3. 点背包：识别坐标，写法跟纯坐标点击完全一样
            daily.addStep(new ActionStep(new Tap(bagIcon, InputButton.LEFT, 80)));

            // 4. 从"第一件装备"的位置往上滑 400px，400ms 分 24 步
            daily.addStep(new ActionStep(
                    Swipe.offsetBy(itemTpl, 0, -400, InputButton.LEFT, 400, 24)));

            // 5. 关弹窗
            daily.addStep(new ActionStep(new Tap(
                    Locator.retry(closeBtn, 1000, 200), InputButton.LEFT, 80)));

            boolean ok = daily.exec();
            System.out.println(ok ? "每日签到完成" : "流程中止：" + daily.getContext().failureMessage());
        }
    }

    /** 四个模板缺任何一个都直接给出路径提示，不要让它变成一条栈。 */
    private static boolean templatesReady() {
        List<String> missing = new ArrayList<>();
        for (String name : TEMPLATES) {
            if (DailySignInExample.class.getResource("/tpl/" + name) == null) {
                missing.add("src/main/resources/tpl/" + name);
            }
        }
        if (missing.isEmpty()) {
            return true;
        }

        System.out.println("缺少模板图片，示例未执行：");
        missing.forEach(p -> System.out.println("  " + p));
        System.out.println("把游戏里对应的截图放进 src/main/resources/tpl/ 后重新运行。");
        return false;
    }

    /** 从 classpath 读图，与运行目录无关；打成 jar 后同样可用。 */
    private static Mat load(String fileName) {
        return MatUtils.loadResourceImage("/tpl/" + fileName);
    }
}
