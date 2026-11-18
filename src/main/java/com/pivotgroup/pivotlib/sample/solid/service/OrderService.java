package com.pivotgroup.pivotlib.sample.solid.service;

import com.pivotgroup.pivotlib.sample.solid.domain.Item;
import com.pivotgroup.pivotlib.sample.solid.domain.Order;
import com.pivotgroup.pivotlib.sample.solid.domain.User;

import java.util.List;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public interface OrderService {

    /**
     * Creates an order containing all supplied items.
     *
     * @param user order owner
     * @param items ordered items
     * @return created order
     */
    Order createOrder(User user, List<Item> items);

}
