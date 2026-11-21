package com.pivotgroup.pivotlib.vision;

import com.pivotgroup.pivotlib.model.Location;

import java.util.Optional;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public interface Identifier {

    Optional<Location> identify(byte[] canvas, byte[] template);

}
