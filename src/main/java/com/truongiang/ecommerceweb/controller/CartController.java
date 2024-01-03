package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.model.Cart;
import com.truongiang.ecommerceweb.service.CartService;
import com.truongiang.ecommerceweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@GetMapping("/user/{email}")
	public ResponseEntity<Cart> getCartUser(@PathVariable("email") String email) {

		if (!this.userService.checkExistedUserByEmail(email)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.cartService.findCartByUser(this.userService.getUserByEmail(email).get()));

	}

	@PutMapping("/user/{email}")
	public ResponseEntity<Cart> putCartUser(@PathVariable("email") String email, @RequestBody Cart cart) {

		if (!this.userService.checkExistedUserByEmail(email)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.cartService.saveCart(cart));

	}

}
