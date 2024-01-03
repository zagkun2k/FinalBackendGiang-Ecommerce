package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.dto.CategoryBestSeller;
import com.truongiang.ecommerceweb.dto.Statistical;
import com.truongiang.ecommerceweb.model.Order;
import com.truongiang.ecommerceweb.model.Product;
import com.truongiang.ecommerceweb.service.OrderService;
import com.truongiang.ecommerceweb.service.ProductService;
import com.truongiang.ecommerceweb.service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/statistical")
public class StatisticalController {

	@Autowired
	private StatisticalService statisticalService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@GetMapping("{year}")
	public ResponseEntity<List<Statistical>> getStatisticalYear(@PathVariable("year") int year) {

		List<Statistical> currentStatList = this.statisticalService.getStatListByYear(year);
		return ResponseEntity.ok(currentStatList);

	}

	@GetMapping("/countYear")
	public ResponseEntity<List<Integer>> getYears() {

		return ResponseEntity.ok(this.statisticalService.getStatisticalYears());

	}

	@GetMapping("/revenue/year/{year}")
	public ResponseEntity<Double> getRevenueByYear(@PathVariable("year") int year) {

		return ResponseEntity.ok(this.statisticalService.getStatisticalRevenueByYear(year));

	}

	@GetMapping("/get-all-order-success")
	public ResponseEntity<List<Order>> getAllOrderSuccess() {

		return ResponseEntity.ok(this.orderService.getOrdersByStatus(2));

	}

	@GetMapping("/get-category-seller")
	public ResponseEntity<List<CategoryBestSeller>> getCategoryBestSeller() {

		List<CategoryBestSeller> bestSellerCategory = this.statisticalService.getBestSellerCategory();
		return ResponseEntity.ok(bestSellerCategory);

	}

	@GetMapping("/get-inventory")
	public ResponseEntity<List<Product>> getInventory() {

		return ResponseEntity.ok(this.productService.findProductsByStatusTrueOrderByQuantityDesc());

	}

}
