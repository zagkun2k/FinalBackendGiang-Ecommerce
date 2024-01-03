package com.truongiang.ecommerceweb.repository;

import com.truongiang.ecommerceweb.model.Cart;
import com.truongiang.ecommerceweb.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

	List<CartDetail> findByCart(Cart cart);

}
