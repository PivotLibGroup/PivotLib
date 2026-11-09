package com.pivotgroup.pivotlib.util;

import com.pivotgroup.pivotlib.util.coordinate.Loc;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/9 11:00
 * @description
 */

public class MaxLocResult {

    public final double maxVal;

    public final Loc maxLoc;

    public MaxLocResult(double maxVal, Loc maxLoc) {
        this.maxVal = maxVal;
        this.maxLoc = maxLoc;
    }

}
