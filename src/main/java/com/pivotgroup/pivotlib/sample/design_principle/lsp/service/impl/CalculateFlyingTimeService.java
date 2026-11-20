package com.pivotgroup.pivotlib.sample.design_principle.lsp.service.impl;

import com.pivotgroup.pivotlib.sample.design_principle.lsp.service.CalculateTimeService;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class CalculateFlyingTimeService implements CalculateTimeService {

    @Override
    public double calculate(double flyingSpeed, double distance){
        return flyingSpeed / distance;
    }

}
