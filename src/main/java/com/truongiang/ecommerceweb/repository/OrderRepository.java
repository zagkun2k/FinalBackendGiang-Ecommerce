package com.truongiang.ecommerceweb.repository;

import com.truongiang.ecommerceweb.model.Order;
import com.truongiang.ecommerceweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUserOrderByOrdersIdDesc(User user);

	List<Order> findAllByOrderByOrdersIdDesc();

	List<Order> findByStatus(int status);

}
