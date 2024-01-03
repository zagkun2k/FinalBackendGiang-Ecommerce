package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.model.Order;
import com.truongiang.ecommerceweb.model.OrderDetail;
import com.truongiang.ecommerceweb.repository.OrderDetailRepository;
import com.truongiang.ecommerceweb.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Override
    public List<OrderDetail> findOrdersDetailByOrder(Order order) {

        return this.orderDetailRepository.findByOrder(order);

    }

    @Override
    public OrderDetail saveOrderDetails(OrderDetail orderDetail) {

        this.orderDetailRepository.save(orderDetail);
        return orderDetail;

    }

    @Override
    public boolean checkExistedOrderDetailById(Long id) {

        return this.orderDetailRepository.existsById(id);

    }

    @Override
    public Optional<OrderDetail> findOrderDetailById(Long id) {

        return this.orderDetailRepository.findById(id);

    }

}
