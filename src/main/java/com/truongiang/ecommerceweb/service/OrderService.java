package com.truongiang.ecommerceweb.service;

import com.truongiang.ecommerceweb.model.Cart;
import com.truongiang.ecommerceweb.model.Order;
import com.truongiang.ecommerceweb.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAllOrdersByOrderByOrdersIdDesc();

    List<Order> findOrdersByUserOrderByOrdersIdDesc(User user);

    boolean checkExistedOrderById(Long id);

    Optional<Order> getOrderById(Long id);

    Order save(Order order);

    Order getCheckOutOrder(String email, Cart cart);

    List<Order> getOrdersByStatus(int status);

}
