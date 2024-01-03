package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.dto.CategoryBestSeller;
import com.truongiang.ecommerceweb.dto.Statistical;
import com.truongiang.ecommerceweb.repository.StatisticalRepository;
import com.truongiang.ecommerceweb.service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class StatisticalServiceImpl implements StatisticalService {

    @Autowired
    private StatisticalRepository statisticalRepository;

    @Override
    public List<Object[]> getStatisticalMonthOfYear(int year) {

        return this.statisticalRepository.getMonthOfYear(year);

    }

    @Override
    public List<Integer> getStatisticalYears() {

        return this.statisticalRepository.getYears();

    }

    @Override
    public Double getStatisticalRevenueByYear(int year) {

        return this.statisticalRepository.getRevenueByYear(year);

    }

    @Override
    public List<Object[]> getStatisticalCategoryBestSeller() {

        return this.statisticalRepository.getCategoryBestSeller();

    }

    @Override
    public List<Statistical> getStatListByYear(int year) {

        List<Object[]> listStatMonths = this.getStatisticalMonthOfYear(year);
        List<Statistical> listStatistical = new ArrayList<>();
        List<Statistical> listStatCurrent = new ArrayList<>();

        for (int i = 0; i < listStatMonths.size(); i++) {

            Statistical statistic = new Statistical((int) listStatMonths.get(i)[1], null, (Double) listStatMonths.get(i)[0], 0);
            listStatistical.add(statistic);
        }

        for (int i = 1; i < 13; i++) {

            Statistical statistic = new Statistical(i, null, 0.0, 0);
            for (int y = 0; y < listStatistical.size(); y++) {

                if (listStatistical.get(y).getMonth() == i) {

                    listStatCurrent.remove(statistic);
                    listStatCurrent.add(listStatistical.get(y));
                    break;
                } else {

                    listStatCurrent.remove(statistic);
                    listStatCurrent.add(statistic);
                }
            }
        }

        return listStatCurrent;

    }

    @Override
    public List<CategoryBestSeller> getBestSellerCategory() {

        List<Object[]> list = this.getStatisticalCategoryBestSeller();
        List<CategoryBestSeller> listCategoryBestSeller = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            CategoryBestSeller categoryBestSeller = new CategoryBestSeller(String.valueOf(list.get(i)[1]),
                    Integer.valueOf(String.valueOf(list.get(i)[0])), Double.valueOf(String.valueOf(list.get(i)[2])));
            listCategoryBestSeller.add(categoryBestSeller);
        }

        return listCategoryBestSeller;

    }

}
