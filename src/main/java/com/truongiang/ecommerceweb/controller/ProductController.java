package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.model.Category;
import com.truongiang.ecommerceweb.model.Product;
import com.truongiang.ecommerceweb.service.CategoryService;
import com.truongiang.ecommerceweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<Product>> getAll() {

		return ResponseEntity.ok(this.productService.findProductsByStatusTrue());

	}

	@GetMapping("bestseller")
	public ResponseEntity<List<Product>> getBestSeller() {

		return ResponseEntity.ok(this.productService.findProductsByStatusTrueOrderBySoldDesc());

	}

	@GetMapping("bestseller-admin")
	public ResponseEntity<List<Product>> getBestSellerAdmin() {

		return ResponseEntity.ok(this.productService.findTop10ProductsByOrderBySoldDesc());

	}

	@GetMapping("latest")
	public ResponseEntity<List<Product>> getLasted() {

		return ResponseEntity.ok(this.productService.findProductsByStatusTrueOrderByEnteredDateDesc());

	}

	@GetMapping("rated")
	public ResponseEntity<List<Product>> getRated() {

		return ResponseEntity.ok(this.productService.findProductsRated());

	}

	@GetMapping("suggest/{categoryId}/{productId}")
	public ResponseEntity<List<Product>> suggest(@PathVariable("categoryId") Long categoryId,
			@PathVariable("productId") Long productId) {

		return ResponseEntity.ok(this.productService.findProductsSuggest(categoryId, productId, categoryId, categoryId));

	}

	@GetMapping("category/{id}")
	public ResponseEntity<List<Product>> getByCategory(@PathVariable("id") Long id) {

		if (!this.categoryService.checkExistedCategoryById(id)) {

			return ResponseEntity.notFound().build();
		}

		Category category = this.categoryService.findCategoryById(id).get();

		List<Product> validProduct = new ArrayList<>();
		for (Product item : this.productService.findProductsByCategory(category)) {

			if (item.getStatus() == true) {

				validProduct.add(item);
			}
		}

		if (validProduct.size() == 0) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(validProduct);

	}

	@GetMapping("{id}")
	public ResponseEntity<Product> getById(@PathVariable("id") Long id) {

		if (!this.productService.checkProductExistedById(id)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.productService.getProductById(id).get());

	}

	@PostMapping
	public ResponseEntity<Product> post(@RequestBody Product product) {

		if (this.productService.checkProductExistedById(product.getProductId())) {

			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(this.productService.saveProduct(product));

	}

	@PutMapping("{id}")
	public ResponseEntity<Product> put(@PathVariable("id") Long id, @RequestBody Product product) {

		if (!id.equals(product.getProductId())) {

			return ResponseEntity.badRequest().build();
		}

		if (!this.productService.checkProductExistedById(id)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.productService.saveProduct(product));

	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		if (!this.productService.checkProductExistedById(id)) {

			return ResponseEntity.notFound().build();
		}

		Product product = this.productService.getProductById(id).get();
		product.setStatus(false);
		this.productService.saveProduct(product);

		return ResponseEntity.ok().build();

	}

	@GetMapping("search-by-name/{name}")
	public ResponseEntity<List<Product>> searchProductsByName(@PathVariable(value = "name" , required = false) String name) {

		List<Product> validProduct = new ArrayList<>();
		for (Product item : this.productService.findProductsByName(name)) {

			if (item.getStatus() != false) {

				validProduct.add(item);
			}
		}

		return ResponseEntity.ok(validProduct);

	}

}