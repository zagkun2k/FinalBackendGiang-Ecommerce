package com.truongiang.ecommerceweb.repository;

import com.truongiang.ecommerceweb.model.Cart;
import com.truongiang.ecommerceweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUser(User user);

}
