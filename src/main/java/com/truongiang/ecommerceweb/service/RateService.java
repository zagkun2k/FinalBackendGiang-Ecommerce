package com.truongiang.ecommerceweb.service;

import com.truongiang.ecommerceweb.model.OrderDetail;
import com.truongiang.ecommerceweb.model.Product;
import com.truongiang.ecommerceweb.model.Rate;

import java.util.List;

public interface RateService {

    List<Rate> findAllRatesByOrderByIdDesc();

    Rate findRateByOrderDetail(OrderDetail orderDetail);

    List<Rate> findRatesByProductOrderByIdDesc(Product product);

    Rate saveRate(Rate rate);

    boolean checkExistedRateById(Long id);

    void deleteSpecRateById(Long id);

}
