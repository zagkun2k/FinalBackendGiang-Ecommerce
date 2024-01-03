package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.model.Cart;
import com.truongiang.ecommerceweb.model.CartDetail;
import com.truongiang.ecommerceweb.repository.CartDetailRepository;
import com.truongiang.ecommerceweb.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    CartDetailRepository cartDetailRepository;

    @Override
    public List<CartDetail> findCartsDetailByCart(Cart cart) {

        return this.cartDetailRepository.findByCart(cart);

    }

    @Override
    public boolean checkCartDetailExistedById(Long id) {

        return this.cartDetailRepository.existsById(id);

    }

    @Override
    public Optional<CartDetail> findCartDetailById(Long id) {

        return this.cartDetailRepository.findById(id);

    }

    @Override
    public CartDetail saveCartDetail(CartDetail cartDetail) {

        this.cartDetailRepository.save(cartDetail);
        return cartDetail;

    }

    @Override
    public void deleteSpecCartDetailById(Long id) {

        this.cartDetailRepository.deleteById(id);

    }

    @Override
    public void deleteSpecCart(CartDetail cartDetail) {

        this.cartDetailRepository.delete(cartDetail);

    }

}
