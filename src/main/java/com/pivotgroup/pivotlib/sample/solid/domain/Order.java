package com.pivotgroup.pivotlib.sample.solid.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/18 12:03
 */

public class Order {

    private final String orderId;
    private final User user;
    private final List<Item> items;

    public Order(String orderId, User user, List<Item> items) {
        this.orderId = Objects.requireNonNull(orderId);
        this.user = Objects.requireNonNull(user);
        this.items = new ArrayList<>(Objects.requireNonNull(items, "items"));
    }

    public BigDecimal calculateTotalPrice() {
        return items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getOrderId() {
        return orderId;
    }

    public List<Item> getItems() {
        return List.copyOf(items);
    }

    public User getUser() {
        return user;
    }

}
