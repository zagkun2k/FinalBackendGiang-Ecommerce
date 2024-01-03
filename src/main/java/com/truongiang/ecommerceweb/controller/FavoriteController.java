package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.model.Favorite;
import com.truongiang.ecommerceweb.model.Product;
import com.truongiang.ecommerceweb.model.User;
import com.truongiang.ecommerceweb.service.FavoriteService;
import com.truongiang.ecommerceweb.service.ProductService;
import com.truongiang.ecommerceweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/favorites")
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@GetMapping("email/{email}")
	public ResponseEntity<List<Favorite>> findByEmail(@PathVariable("email") String email) {

		if (this.userService.checkExistedUserByEmail(email)) {

			return ResponseEntity.ok(this.favoriteService.findFavoritesByUser(this.userService.getUserByEmail(email).get()));
		}

		return ResponseEntity.notFound().build();

	}

	@GetMapping("product/{id}")
	public ResponseEntity<Integer> findByProduct(@PathVariable("id") Long id) {

		if (this.productService.checkProductExistedById(id)) {

			return ResponseEntity.ok(this.favoriteService.countFavoritesByProduct(this.productService.getProductById(id).get()));
		}

		return ResponseEntity.notFound().build();

	}

	@GetMapping("{productId}/{email}")
	public ResponseEntity<Favorite> findByProductAndUser(@PathVariable("productId") Long productId,
			@PathVariable("email") String email) {

		if (this.userService.checkExistedUserByEmail(email)) {

			if (this.productService.checkProductExistedById(productId)) {

				Product product = this.productService.getProductById(productId).get();
				User user = this.userService.getUserByEmail(email).get();
				Favorite favorite = this.favoriteService.findFavoritesByProductAndUser(product, user);

				return ResponseEntity.ok(favorite);
			}
		}

		return ResponseEntity.notFound().build();

	}

	@PostMapping("email")
	public ResponseEntity<Favorite> post(@RequestBody Favorite favorite) {

		return ResponseEntity.ok(this.favoriteService.saveFavorite(favorite));

	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		if (this.favoriteService.checkExistedFavoriteById(id)) {

			this.favoriteService.deleteSpecFavoriteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();

	}

}
