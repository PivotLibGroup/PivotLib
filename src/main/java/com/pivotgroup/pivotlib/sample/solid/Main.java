package com.pivotgroup.pivotlib.sample.solid;

import com.pivotgroup.pivotlib.sample.solid.domain.Item;
import com.pivotgroup.pivotlib.sample.solid.domain.Order;
import com.pivotgroup.pivotlib.sample.solid.domain.User;
import com.pivotgroup.pivotlib.sample.solid.infrastructure.SequentialOrderIdGenerator;
import com.pivotgroup.pivotlib.sample.solid.service.OrderService;
import com.pivotgroup.pivotlib.sample.solid.service.impl.OrderServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/18 12:03
 */

public class Main {
    public static void main(String[] args) {
        User user = new User("小张", "3536370493@qq.com");
        Item item1 = new Item("item_1", new BigDecimal(18), "玩具小熊");
        Item item2 = new Item("item_2", new BigDecimal(45), "液压挖掘机玩具");

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        OrderService orderService = new OrderServiceImpl(new SequentialOrderIdGenerator());
        Order order = orderService.createOrder(user, items);

        BigDecimal totalPrice = order.calculateTotalPrice();
        System.out.println("order's total price is: " + totalPrice.signum());
    }
}
