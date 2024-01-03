package com.truongiang.ecommerceweb.service;

import com.truongiang.ecommerceweb.model.Order;
import com.truongiang.ecommerceweb.model.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {

    List<OrderDetail> findOrdersDetailByOrder(Order order);

    OrderDetail saveOrderDetails(OrderDetail orderDetail);

    boolean checkExistedOrderDetailById(Long id);

    Optional<OrderDetail> findOrderDetailById(Long id);

}
