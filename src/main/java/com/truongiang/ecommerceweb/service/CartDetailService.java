package com.truongiang.ecommerceweb.service;

import com.truongiang.ecommerceweb.model.Cart;
import com.truongiang.ecommerceweb.model.CartDetail;

import java.util.List;
import java.util.Optional;

public interface CartDetailService {

    List<CartDetail> findCartsDetailByCart(Cart cart);

    boolean checkCartDetailExistedById(Long id);

    Optional<CartDetail> findCartDetailById(Long id);

    CartDetail saveCartDetail(CartDetail cartDetail);

    void deleteSpecCartDetailById(Long id);

    void deleteSpecCart(CartDetail cartDetail);

}
