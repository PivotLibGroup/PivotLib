package com.pivotgroup.pivotlib.sample.solid.infrastructure;

import com.pivotgroup.pivotlib.sample.solid.service.IdGenerator;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class SequentialOrderIdGenerator implements IdGenerator {

    private final AtomicLong ids = new AtomicLong();

    @Override
    public String nextId() {
        return "order_" + ids.getAndIncrement();
    }

}
