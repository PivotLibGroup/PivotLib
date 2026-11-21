package com.pivotgroup.pivotlib.action;

import com.pivotgroup.pivotlib.model.Location;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/12 16:28
 */

public interface TouchCapturable {

    /**
     * 触点按下
     * @param button 按下的键
     */
    void down(int button);

    /**
     * 触点释放
     * @param button 按下的键
     */
    void release(int button);

    /**
     * 触点移动
     * @param x x坐标
     * @param y y坐标
     */
    void move(int x, int y);

    /**
     * 得到鼠标的实际位置
     * @return 位置
     */
    Location getLocation();
}
