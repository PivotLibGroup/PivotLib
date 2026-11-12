package com.pivotgroup.pivotlib_old.util;

import com.pivotgroup.pivotlib_old.util.coordinate.Loc;
import org.opencv.core.Mat;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/8 01:32
 * @description
 */

public class MatchInfo {

    public final Mat canvas;
    public final Mat template;
    public final Loc loc;

    public MatchInfo(Mat canvas, Mat template, Loc loc) {
        this.canvas = canvas;
        this.template = template;
        this.loc = loc;
    }
}
