package com.pivotgroup.pivotlib.model;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/15 12:41
 */

public class ResultLoc {

    public final double maxVal;
    public final Location loc;

    public ResultLoc(double maxVal, Location loc) {
        this.maxVal = maxVal;
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "ResultLoc{" +
                "maxVal=" + maxVal +
                ", loc=" + loc +
                '}';
    }
}
