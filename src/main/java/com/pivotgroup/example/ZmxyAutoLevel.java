package com.pivotgroup.example;

import com.pivotgroup.pivotlib.capture.ScreenCapturer;
import com.pivotgroup.pivotlib.device.robot.RobotInputDevice;
import com.pivotgroup.pivotlib.device.robot.RobotScreenCapturer;
import com.pivotgroup.pivotlib.input.InputButton;
import com.pivotgroup.pivotlib.input.Locator;
import com.pivotgroup.pivotlib.input.Pointer;
import com.pivotgroup.pivotlib.input.gesture.Tap;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.util.MatUtils;
import com.pivotgroup.pivotlib.util.MaxLocResult;
import com.pivotgroup.pivotlib.vision.TemplateLocator;
import com.pivotgroup.pivotlib.workflow.Workflow;
import com.pivotgroup.pivotlib.workflow.step.ActionStep;
import org.opencv.core.Mat;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/9 21:46
 * @description
 */

public class ZmxyAutoLevel {

    static Pointer pointer = new Pointer(new RobotInputDevice());
    static ScreenCapturer screenCapturer = new ScreenCapturer(new RobotScreenCapturer());

    static TemplateLocator zhanBtn = new TemplateLocator(screenCapturer, load("zmxy/zhan.png"));
    static TemplateLocator nanzhanbuzhou = new TemplateLocator(screenCapturer, load("zmxy/nanzhanbuzhou.png"));
    static TemplateLocator wuxingjie = new TemplateLocator(screenCapturer, load("zmxy/wuxingjie.png"));
    static TemplateLocator go_right = new TemplateLocator(screenCapturer, load("zmxy/move/go_right.png"));
    static TemplateLocator go_left = new TemplateLocator(screenCapturer, load("zmxy/move/go_left.png"));
    static TemplateLocator qianwang = new TemplateLocator(screenCapturer, load("zmxy/qianwang.png"));
    static TemplateLocator tiaozhan = new TemplateLocator(screenCapturer, load("zmxy/tiaozhan.png"));
    static TemplateLocator fanhuizhucheng = new TemplateLocator(screenCapturer, load("zmxy/fanhuizhucheng.png"));

    // src/java/resources/tx.jpg
    static Mat qianwangMat = MatUtils.loadImage("src/main/resources/tpl/zmxy/qianwang.png");
    static Mat shengli = MatUtils.loadImage("src/main/resources/tpl/zmxy/shengli.png");
    static Mat tongguan = MatUtils.loadImage("src/main/resources/tpl/zmxy/tongguan.png");

    static boolean isVictory = false;

    public static void main(String[] args) {
        while (true) {
            isVictory = false;

            gotoLevel();
            // 此时已经进入游戏
            while (true) {
                if (ifShengli() || ifTongguan()) {
                    isVictory = true;
                    // 返回主城
                    goBack();
                    break;
                }
                autoMove();
                while (!canMove()) {
                    useSkill();
                }
            }
        }
    }

    private static boolean ifTongguan() {
        Mat capture = screenCapturer.capture();
        MaxLocResult maxLocResult = MatMatchUtils.matchMultiScale(capture, tongguan);
        if (maxLocResult.maxVal <= MatMatchUtils.DEFAULT_MIN_SCORE) {
            return false;
        }
        return true;
    }

    private static boolean ifShengli() {
        Mat capture = screenCapturer.capture();
        MaxLocResult maxLocResult = MatMatchUtils.matchMultiScale(capture, shengli);
        if (maxLocResult.maxVal <= MatMatchUtils.DEFAULT_MIN_SCORE) {
            return false;
        }
        return true;
    }

    private static boolean canMove() {
        Mat capture = screenCapturer.capture();
        MaxLocResult maxLocResult = MatMatchUtils.matchMultiScale(capture, qianwangMat);
        if (maxLocResult.maxVal <= MatMatchUtils.DEFAULT_MIN_SCORE) {
            return false;
        }
        return true;
    }

    private static void goBack() {
        Workflow goBack = new Workflow("go_back", pointer, screenCapturer);
        goBack.addStep(new ActionStep(new Tap(Locator.retry(fanhuizhucheng,
                10000, 100), InputButton.LEFT, 500)));
        goBack.exec();
    }

    private static void autoMove() {
        // 自动打副本步骤
        Workflow autoMove = new Workflow("auto_move", pointer, screenCapturer);
        // 前进 5 秒
        autoMove.addStep(new ActionStep(new Tap(Locator.retry(go_right,
                10000, 100), InputButton.LEFT, 5000)));
        // 转向
        autoMove.addStep(new ActionStep(new Tap(Locator.retry(go_left,
                10000, 100), InputButton.LEFT, 50)));
        autoMove.exec();
    }

    private static void gotoLevel() {
        // 进入关卡步骤
        Workflow gotoLevel = new Workflow("goto_level", pointer, screenCapturer);
        // 点击"战"
        gotoLevel.addStep(new ActionStep(new Tap(Locator.retry(zhanBtn,
                10000, 100), InputButton.LEFT, 100)));
        // 点击"南瞻部周"
        gotoLevel.addStep(new ActionStep(new Tap(Locator.retry(nanzhanbuzhou,
                10000, 100), InputButton.LEFT, 100)));
        // 点击指定关卡
        gotoLevel.addStep(new ActionStep(new Tap(Locator.retry(wuxingjie,
                10000, 100), InputButton.LEFT, 100)));
        // 点击"挑战"
        gotoLevel.addStep(new ActionStep(new Tap(Locator.retry(tiaozhan,
                10000, 100), InputButton.LEFT, 100)));
        gotoLevel.exec();
    }


    private static void useSkill() {
        if (isVictory || ifShengli()) {
            isVictory = true;
            return;
        }
        // 释放技能
        Workflow useSkill = new Workflow("use_skill", pointer, screenCapturer);
        // 2300 1400: 普通攻击
        useSkill.addStep(new ActionStep(new Tap(1550, 900, InputButton.LEFT, 500)));
        if (isVictory || ifShengli() || ifTongguan()) {
            isVictory = true;
            return;
        }
        // 1830 1425: 一技能
        useSkill.addStep(new ActionStep(new Tap(1200, 925, InputButton.LEFT, 500)));
        if (isVictory || ifShengli() || ifTongguan()) {
            isVictory = true;
            return;
        }
        // 650 400
        // 1900 1222: 二技能
        useSkill.addStep(new ActionStep(new Tap(1250, 822, InputButton.LEFT, 500)));
        if (isVictory || ifShengli() || ifTongguan()) {
            isVictory = true;
            return;
        }
        // 2115 1146: 三技能
        useSkill.addStep(new ActionStep(new Tap(1405, 746, InputButton.LEFT, 500)));
        useSkill.exec();
    }

    private static Mat load(String fileName) {
        return MatUtils.loadResourceImage("/tpl/" + fileName);
    }
}
