package com.truongiang.ecommerceweb.repository;

import com.truongiang.ecommerceweb.model.Order;
import com.truongiang.ecommerceweb.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	List<OrderDetail> findByOrder(Order order);

}