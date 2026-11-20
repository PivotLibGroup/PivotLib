package com.pivotgroup.pivotlib.sample.design_principle.lsp.service.impl;

import com.pivotgroup.pivotlib.sample.design_principle.lsp.service.CalculateTimeService;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class CalculateRunTimeService implements CalculateTimeService {

    @Override
    public double calculate(double runSpeed, double distance){
        return runSpeed / distance;
    }

}
