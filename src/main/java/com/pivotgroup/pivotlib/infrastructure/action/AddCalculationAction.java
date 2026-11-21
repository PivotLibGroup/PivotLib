package com.pivotgroup.pivotlib.infrastructure.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class AddCalculationAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AddCalculationAction.class);
    private final double left;

    private final double right;

    public AddCalculationAction(double left, double right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void apply() {
        double result = left + right;
        log.info(String.format("%f + %f = %f", left, right, result));
    }
}
