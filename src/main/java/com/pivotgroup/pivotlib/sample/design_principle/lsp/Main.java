package com.pivotgroup.pivotlib.sample.design_principle.lsp;

import com.pivotgroup.pivotlib.sample.design_principle.lsp.animal.Ostrich;
import com.pivotgroup.pivotlib.sample.design_principle.lsp.animal.Parrot;
import com.pivotgroup.pivotlib.sample.design_principle.lsp.service.CalculateTimeService;
import com.pivotgroup.pivotlib.sample.design_principle.lsp.service.impl.CalculateFlyingTimeService;
import com.pivotgroup.pivotlib.sample.design_principle.lsp.service.impl.CalculateRunTimeService;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class Main {

    public static void main(String[] args) {
        CalculateTimeService calculateRunTimeService = new CalculateRunTimeService();
        CalculateTimeService calculateFlyingTimeService = new CalculateFlyingTimeService();

        // 鸵鸟
        Animal ostrich = new Ostrich();
        ostrich.setName("小强");
        ostrich.setRunSpeed(10);
        thenRunTime(calculateRunTimeService.calculate(ostrich.getRunSpeed(), 100));

        // 鹦鹉
        FlyingAnimal parrot = new Parrot();
        parrot.setName("爱华");
        parrot.setFlySpeed(50);
        parrot.setRunSpeed(10);
        thenRunTime(calculateRunTimeService.calculate(parrot.getRunSpeed(), 300));
        thenFlyingTime(calculateRunTimeService.calculate(parrot.getFlySpeed(), 300));
    }

    public static void thenRunTime(double time) {
        System.out.println("行走耗时:" + time + "小时");
    }

    public static void thenFlyingTime(double time) {
        System.out.println("飞行耗时:" + time + "小时");
    }

}
