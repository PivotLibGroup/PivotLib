package com.pivotgroup.pivotlib.vision;

import com.pivotgroup.pivotlib.model.Location;

import java.util.Optional;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public interface Locater {

    static Optional<Location> find(Locater locater) {
        return locater.find();
    }

    Optional<Location> find();
}
