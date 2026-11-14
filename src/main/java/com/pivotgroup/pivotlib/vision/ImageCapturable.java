package com.pivotgroup.pivotlib.vision;

import org.opencv.core.Mat;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 16:07
 */

public interface ImageCapturable {

    /**
     * 捕获全部画面
     * @return
     */
    Mat capture();

    /**
     * 按指定位置宽高捕获画面
     * @param x x坐标
     * @param y y坐标
     * @param width 宽度
     * @param height 高度
     * @return
     */
    Mat capture(int x, int y, int width, int height);

}
