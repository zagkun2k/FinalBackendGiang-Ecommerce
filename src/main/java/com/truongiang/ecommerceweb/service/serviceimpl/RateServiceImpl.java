package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.model.OrderDetail;
import com.truongiang.ecommerceweb.model.Product;
import com.truongiang.ecommerceweb.model.Rate;
import com.truongiang.ecommerceweb.repository.RateRepository;
import com.truongiang.ecommerceweb.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Override
    public List<Rate> findAllRatesByOrderByIdDesc() {

        return this.rateRepository.findAllByOrderByIdDesc();

    }

    @Override
    public Rate findRateByOrderDetail(OrderDetail orderDetail) {

        return this.rateRepository.findByOrderDetail(orderDetail);

    }

    @Override
    public List<Rate> findRatesByProductOrderByIdDesc(Product product) {

        return this.rateRepository.findByProductOrderByIdDesc(product);

    }

    @Override
    public Rate saveRate(Rate rate) {

        this.rateRepository.save(rate);
        return rate;

    }

    @Override
    public boolean checkExistedRateById(Long id) {

        return this.rateRepository.existsById(id);

    }

    @Override
    public void deleteSpecRateById(Long id) {

        this.rateRepository.deleteById(id);

    }

}
