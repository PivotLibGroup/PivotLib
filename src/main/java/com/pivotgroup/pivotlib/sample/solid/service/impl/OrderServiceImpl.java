package com.pivotgroup.pivotlib.sample.solid.service.impl;

import com.pivotgroup.pivotlib.sample.solid.domain.Item;
import com.pivotgroup.pivotlib.sample.solid.domain.Order;
import com.pivotgroup.pivotlib.sample.solid.domain.User;
import com.pivotgroup.pivotlib.sample.solid.service.IdGenerator;
import com.pivotgroup.pivotlib.sample.solid.service.OrderService;

import java.util.List;
import java.util.Objects;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/18 12:10
 */

public class OrderServiceImpl implements OrderService {

    private final IdGenerator idGenerator;

    public OrderServiceImpl(IdGenerator idGenerator) {
        this.idGenerator = Objects.requireNonNull(idGenerator);
    }

    @Override
    public Order createOrder(User user, List<Item> items) {
        return new Order(idGenerator.nextId(), user, items);
    }

}
