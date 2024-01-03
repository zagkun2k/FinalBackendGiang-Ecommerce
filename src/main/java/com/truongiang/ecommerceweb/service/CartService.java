package com.truongiang.ecommerceweb.service;

import com.truongiang.ecommerceweb.model.Cart;
import com.truongiang.ecommerceweb.model.User;

import java.util.Optional;

public interface CartService {

    Cart findCartByUser(User user);

    Cart saveCart(Cart cart);

    boolean checkCartExistedById(Long id);

    Optional<Cart> findCartById(Long id);

}
