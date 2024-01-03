package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.model.Rate;
import com.truongiang.ecommerceweb.service.OrderDetailService;
import com.truongiang.ecommerceweb.service.ProductService;
import com.truongiang.ecommerceweb.service.RateService;
import com.truongiang.ecommerceweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/rates")
public class RateController {

	@Autowired
	private RateService rateService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<Rate>> findAll() {

		return ResponseEntity.ok(this.rateService.findAllRatesByOrderByIdDesc());

	}

	@GetMapping("{orderDetailId}")
	public ResponseEntity<Rate> findById(@PathVariable Long orderDetailId) {

		if (!this.orderDetailService.checkExistedOrderDetailById(orderDetailId)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.rateService.findRateByOrderDetail(this.orderDetailService.findOrderDetailById(orderDetailId).get()));

	}

	@GetMapping("/product/{id}")
	public ResponseEntity<List<Rate>> findByProduct(@PathVariable("id") Long id) {

		if (!this.productService.checkProductExistedById(id)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.rateService.findRatesByProductOrderByIdDesc(this.productService.getProductById(id).get()));

	}

	@PostMapping
	public ResponseEntity<Rate> post(@RequestBody Rate rate) {

		if (!this.userService.checkExistedUserById(rate.getUser().getUserId())) {

			return ResponseEntity.notFound().build();
		}

		if (!this.productService.checkProductExistedById(rate.getProduct().getProductId())) {

			return ResponseEntity.notFound().build();
		}

		if (!this.orderDetailService.checkExistedOrderDetailById(rate.getOrderDetail().getOrderDetailId())) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.rateService.saveRate(rate));

	}

	@PutMapping
	public ResponseEntity<Rate> put(@RequestBody Rate rate) {

		if (!this.rateService.checkExistedRateById(rate.getId())) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.rateService.saveRate(rate));

	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		if (!this.rateService.checkExistedRateById(id)) {

			return ResponseEntity.notFound().build();
		}

		this.rateService.deleteSpecRateById(id);
		return ResponseEntity.ok().build();

	}

}
