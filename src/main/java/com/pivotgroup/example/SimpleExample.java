package com.pivotgroup.example;

import com.pivotgroup.pivotlib.capture.ScreenCapturer;
import com.pivotgroup.pivotlib.device.robot.RobotInputDevice;
import com.pivotgroup.pivotlib.device.robot.RobotScreenCapturer;
import com.pivotgroup.pivotlib.input.InputButton;
import com.pivotgroup.pivotlib.input.Locator;
import com.pivotgroup.pivotlib.input.Pointer;
import com.pivotgroup.pivotlib.input.gesture.Tap;
import com.pivotgroup.pivotlib.util.MatUtils;
import com.pivotgroup.pivotlib.vision.TemplateLocator;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.ActionStep;
import com.pivotgroup.pivotlib.workflow.step.WaitStep;
import org.opencv.core.Mat;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/9 16:58
 * @description 最小识别点击示例，模板取自 classpath 的 /tpl/
 */
public class SimpleExample {

    public static void main(String[] args) {

        Pointer pointer = new Pointer(new RobotInputDevice());
        ScreenCapturer screenCapturer = new ScreenCapturer(new RobotScreenCapturer());

        try (TemplateLocator startBtn = new TemplateLocator(screenCapturer, load("test_file.png"));
             TemplateLocator start = new TemplateLocator(screenCapturer, load("start.png"));
             TemplateLocator gotoGame = new TemplateLocator(screenCapturer, load("goto_game.png"));
             TemplateLocator chongwu = new TemplateLocator(screenCapturer, load("chongwu.png"))) {

            Workflow workflow = new Workflow("example", pointer, screenCapturer);

            // 进入游戏
            workflow.addStep(new ActionStep(new Tap(Locator.retry(gotoGame,
                    10000, 500), InputButton.LEFT, 500)));


            workflow.addStep(new WaitStep(1000));


            workflow.addStep(new ActionStep(new Tap(Locator.retry(start,
                    10000, 500), InputButton.LEFT, 1000)));

            workflow.addStep(new WaitStep(10000));

            workflow.addStep(new ActionStep(new Tap(0, 0, InputButton.LEFT, 50)));

            workflow.addStep(new WaitStep(1000));

            workflow.addStep(new ActionStep(new Tap(Locator.retry(chongwu,
                    10000, 500), InputButton.LEFT, 1000)));

            /*workflow.addStep(new ActionStep(
                    new Swipe(Locator.retry(startBtn, 10_000, 500),
                            Locator.retry(trashcan, 10_000, 500),
                            InputButton.LEFT, 3000, 100)));
*/
            boolean ok = workflow.exec();
            System.out.println("workflow.exec() = " + ok);
            if (!ok) {
                System.out.println("失败原因：" + workflow.getContext().failureMessage());
            }
        }
    }

    /**
     * 从 classpath 读图：/tpl/chongwu.png 即 src/main/resources/tpl/chongwu.png，与工作目录无关。
     */
    private static Mat load(String fileName) {
        return MatUtils.loadResourceImage("/tpl/" + fileName);
    }
}
