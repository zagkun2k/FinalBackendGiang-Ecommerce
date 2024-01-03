package com.truongiang.ecommerceweb.repository;

import com.truongiang.ecommerceweb.model.OrderDetail;
import com.truongiang.ecommerceweb.model.Product;
import com.truongiang.ecommerceweb.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

	List<Rate> findAllByOrderByIdDesc();

	Rate findByOrderDetail(OrderDetail orderDetail);

	List<Rate> findByProductOrderByIdDesc(Product product);

}
