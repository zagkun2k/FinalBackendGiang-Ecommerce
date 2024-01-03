package com.truongiang.ecommerceweb.repository;

import com.truongiang.ecommerceweb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticalRepository extends JpaRepository<Product, Long> {

	@Query(value = "select sum(amount), month(order_date) from orders where year(order_date) = ? and status = 2 group by month(order_date)", nativeQuery = true)
	List<Object[]> getMonthOfYear(int year);

	@Query(value = "select year(order_date) from orders group by year(order_date)", nativeQuery = true)
	List<Integer> getYears();

	@Query(value = "select sum(amount) from orders where year(order_date) = ? and status = 2", nativeQuery = true)
	Double getRevenueByYear(int year);

	@Query(value = "SELECT SUM(p.sold), c.category_name, SUM(p.price * p.sold) - SUM(p.discount * p.sold) " +
			"FROM categories c " +
			"JOIN products p ON p.category_id = c.category_id " +
			"GROUP BY c.category_name " +
			"ORDER BY SUM(p.sold) DESC", nativeQuery = true)
	List<Object[]> getCategoryBestSeller();

}
