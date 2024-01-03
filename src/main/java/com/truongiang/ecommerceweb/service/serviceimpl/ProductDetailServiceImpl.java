package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.service.ProductDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductDetailServiceImpl implements ProductDetailService {

}
