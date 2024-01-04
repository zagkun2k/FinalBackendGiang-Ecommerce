package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.common.configuration.mail.SendMailUtil;
import com.truongiang.ecommerceweb.model.*;
import com.truongiang.ecommerceweb.repository.CartDetailRepository;
import com.truongiang.ecommerceweb.repository.OrderDetailRepository;
import com.truongiang.ecommerceweb.repository.OrderRepository;
import com.truongiang.ecommerceweb.repository.UserRepository;
import com.truongiang.ecommerceweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CartDetailRepository cartDetailRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SendMailUtil mailUtil;

	@GetMapping
	public ResponseEntity<List<Order>> findAll() {

		return ResponseEntity.ok(this.orderService.findAllOrdersByOrderByOrdersIdDesc());

	}

	@GetMapping("{id}")
	public ResponseEntity<Order> getById(@PathVariable("id") Long id) {

		if (!this.orderService.checkExistedOrderById(id)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.orderService.getOrderById(id).get());

	}

	@GetMapping("/user/{email}")
	public ResponseEntity<List<Order>> getByUser(@PathVariable("email") String email) {

		if (!this.userService.checkExistedUserByEmail(email)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity
				.ok(this.orderService.findOrdersByUserOrderByOrdersIdDesc(this.userService.getUserByEmail(email).get()));

	}

	@PostMapping("/{email}")
	public ResponseEntity<Order> checkout(@PathVariable("email") String email, @RequestBody Cart cart) {

		if (!this.userService.checkExistedUserByEmail(email)) {

			return ResponseEntity.notFound().build();
		}

		if (!cartService.checkCartExistedById(cart.getCartId())) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.getCheckOutOrder(email, cart));

	}

	@GetMapping("cancel/{orderId}")
	public ResponseEntity<Void> cancel(@PathVariable("orderId") Long id) {

		Order order = this.orderService.getOrderById(id).get();
		List<OrderDetail> detail = this.orderDetailRepository.findByOrder(order);
		for (OrderDetail orderDetail : detail) {

			Product product = orderDetail.getProduct();
			product.setQuantity(product.getQuantity() + orderDetail.getQuantity());
			if (product.getQuantity() > 0) {

				product.setStatus(true);
			}
			this.productService.saveProduct(product);
		}

		return this.commonSendMailOrder(id, 3);

	}

	@GetMapping("deliver/{orderId}")
	public ResponseEntity<Void> deliver(@PathVariable("orderId") Long id) {

		return this.commonSendMailOrder(id, 1);

	}

	@GetMapping("success/{orderId}")
	public ResponseEntity<Void> success(@PathVariable("orderId") Long id) {

		return this.commonSendMailOrder(id, 2);

	}

	private ResponseEntity<Void> commonSendMailOrder(Long id, int flag) {

		if (!this.orderService.checkExistedOrderById(id)) {

			return ResponseEntity.notFound().build();
		}

		Order order = this.orderService.getOrderById(id).get();
		order.setStatus(flag);
		this.orderService.save(order);

		switch (flag) {

			case 1:

				this.mailUtil.sendMailOrderDeliver(order);
				break;
			case 2:

				this.mailUtil.sendMailOrderSuccess(order);
				this.productService.updateProductByOrder(order);
				break;
			case 3:

				this.mailUtil.sendMailOrderCancel(order);
				break;
			default:

				break;
		}

		return ResponseEntity.ok().build();

	}

	public Order getCheckOutOrder(String email, Cart cart) {

		List<CartDetail> items = cartDetailRepository.findByCart(cart);
		Double amount = 0.0;

		for (CartDetail i : items) {

			amount += i.getPrice();
		}

		Order order = this.orderRepository.save(new Order(0L, new Date(), amount, cart.getAddress(), cart.getPhone(), 0,
				this.userRepository.findByEmail(email).get()));

		for (CartDetail i : items) {

			OrderDetail orderDetail = new OrderDetail(0L, i.getQuantity(), i.getPrice(), i.getProduct(), order);
			this.orderDetailRepository.save(orderDetail);
		}

		for (CartDetail i : items) {

			this.cartDetailRepository.delete(i);
		}

		this.mailUtil.sendMailOrder(order);
		return order;

	}

}
