package com.pivotgroup.pivotlib.inspection;

import java.util.Optional;

/**
 * Inspects the current state for an important feature.
 *
 * @author xinliu
 */
@FunctionalInterface
public interface FeatureInspector {

    /**
     * Inspects the current state for a feature.
     *
     * @return detected feature, or empty when none is present
     */
    Optional<Feature> inspect();
}
