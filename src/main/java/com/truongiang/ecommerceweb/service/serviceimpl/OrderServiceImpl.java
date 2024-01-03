package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.common.configuration.mail.SendMailUtil;
import com.truongiang.ecommerceweb.model.*;
import com.truongiang.ecommerceweb.repository.OrderRepository;
import com.truongiang.ecommerceweb.service.CartDetailService;
import com.truongiang.ecommerceweb.service.OrderDetailService;
import com.truongiang.ecommerceweb.service.OrderService;
import com.truongiang.ecommerceweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private SendMailUtil mailUtil;


    @Override
    public List<Order> findAllOrdersByOrderByOrdersIdDesc() {

        return this.orderRepository.findAllByOrderByOrdersIdDesc();

    }

    @Override
    public List<Order> findOrdersByUserOrderByOrdersIdDesc(User user) {

        return this.orderRepository.findByUserOrderByOrdersIdDesc(user);

    }

    @Override
    public boolean checkExistedOrderById(Long id) {

        return this.orderRepository.existsById(id);

    }

    @Override
    public Optional<Order> getOrderById(Long id) {

        return this.orderRepository.findById(id);

    }

    @Override
    public Order save(Order order) {

        this.orderRepository.save(order);
        return order;

    }

    @Override
    public Order getCheckOutOrder(String email, Cart cart) {

        List<CartDetail> items = cartDetailService.findCartsDetailByCart(cart);
        Double amount = 0.0;

        for (CartDetail i : items) {

            amount += i.getPrice();
        }

        Order order = this.save(new Order(0L, new Date(), amount, cart.getAddress(), cart.getPhone(), 0,
                this.userService.getUserByEmail(email).get()));

        for (CartDetail i : items) {

            OrderDetail orderDetail = new OrderDetail(0L, i.getQuantity(), i.getPrice(), i.getProduct(), order);
            orderDetailService.saveOrderDetails(orderDetail);
        }

        for (CartDetail i : items) {

            cartDetailService.deleteSpecCart(i);
        }

        mailUtil.sendMailOrder(order);
        return order;

    }

    @Override
    public List<Order> getOrdersByStatus(int status) {

        return this.orderRepository.findByStatus(status);

    }

}
