package com.pivotgroup.pivotlib.vision;

import org.opencv.core.Mat;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 16:07
 */

public interface ImageCapturer {

    /**
     * 捕获画面
     * @return
     */
    Mat capture();

}
