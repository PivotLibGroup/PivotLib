package com.pivotgroup.pivotlib.action;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public interface TouchCapturer {
    void down(int button);
    void release(int button);
    void move(int x, int y);
}
