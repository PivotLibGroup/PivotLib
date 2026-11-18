package com.pivotgroup.pivotlib.sample.solid.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/18 12:04
 */

public class Item {

    private final String itemId;
    private final BigDecimal price;
    private final String itemName;

    public Item(String itemId, BigDecimal price, String itemName) {
        this.itemId = Objects.requireNonNull(itemId);
        this.itemName = Objects.requireNonNull(itemName);
        Objects.requireNonNull(price);
        if (price.signum() < 0) {
            throw new IllegalArgumentException("price must not be negative");
        }
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getItemName() {
        return itemName;
    }

}
