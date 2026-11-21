package com.pivotgroup.pivotlib.infrastructure.vision;

import com.pivotgroup.pivotlib.domain.Location;

import java.util.Optional;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public interface Locater {

    Optional<Location> find();
}
