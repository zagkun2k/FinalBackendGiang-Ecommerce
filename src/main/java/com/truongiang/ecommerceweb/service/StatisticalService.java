package com.truongiang.ecommerceweb.service;

import com.truongiang.ecommerceweb.dto.CategoryBestSeller;
import com.truongiang.ecommerceweb.dto.Statistical;

import java.util.List;

public interface StatisticalService {

    List<Object[]> getStatisticalMonthOfYear(int year);

    List<Integer> getStatisticalYears();

    Double getStatisticalRevenueByYear(int year);

    List<Object[]> getStatisticalCategoryBestSeller();

    List<Statistical> getStatListByYear(int year);

    List<CategoryBestSeller> getBestSellerCategory();

}
