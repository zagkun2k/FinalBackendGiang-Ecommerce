package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.model.Cart;
import com.truongiang.ecommerceweb.model.User;
import com.truongiang.ecommerceweb.repository.CartRepository;
import com.truongiang.ecommerceweb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public Cart findCartByUser(User user) {

        return this.cartRepository.findByUser(user);

    }

    @Override
    public Cart saveCart(Cart cart) {

        return this.cartRepository.save(cart);

    }

    @Override
    public boolean checkCartExistedById(Long id) {

        return this.cartRepository.existsById(id);

    }

    @Override
    public Optional<Cart> findCartById(Long id) {

        return this.cartRepository.findById(id);

    }

}
