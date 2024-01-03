package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.model.OrderDetail;
import com.truongiang.ecommerceweb.service.OrderDetailService;
import com.truongiang.ecommerceweb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/orderDetail")
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private OrderService orderService;

	@GetMapping("/order/{id}")
	public ResponseEntity<List<OrderDetail>> getByOrder(@PathVariable("id") Long id) {

		if (!this.orderService.checkExistedOrderById(id)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(orderDetailService.findOrdersDetailByOrder(this.orderService.getOrderById(id).get()));

	}

}
