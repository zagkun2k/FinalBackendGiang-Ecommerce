package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.model.CartDetail;
import com.truongiang.ecommerceweb.model.Product;
import com.truongiang.ecommerceweb.service.CartDetailService;
import com.truongiang.ecommerceweb.service.CartService;
import com.truongiang.ecommerceweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/cartDetail")
public class CartDetailController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartDetailService cartDetailService;

	@Autowired
	private ProductService productService;

	@GetMapping("cart/{id}")
	public ResponseEntity<List<CartDetail>> getByCartId(@PathVariable("id") Long id) {

		if (!this.cartService.checkCartExistedById(id)) {

			return ResponseEntity.notFound().build();
		}

//		List<CartDetail> currentOrder = this.cartDetailService.findCartsDetailByCart(this.cartService.findCartById(id).get());
//		List<CartDetail> validOrder = new ArrayList<>();
//		for (CartDetail item : currentOrder) {
//
//			if (item.getQuantity() > item.getProduct().getQuantity()) {
//
//				item.setQuantity(item.getProduct().getQuantity());
//				if (item.getQuantity() != 0) {
//
//					validOrder.add(item);
//				}
//			}
//		}

		return ResponseEntity.ok(this.cartDetailService.findCartsDetailByCart(this.cartService.findCartById(id).get()));

	}

	@RequestMapping(value = "{id}/{quantity}", method = RequestMethod.GET)
	public ResponseEntity<CartDetail> getOne(@PathVariable("id") Long id, @PathVariable("quantity") int quantity) {

		if (!this.cartDetailService.checkCartDetailExistedById(id)) {

			return ResponseEntity.notFound().build();
		}

		int quantityProduct = this.cartDetailService.findCartDetailById(id).get().getProduct().getQuantity();
//		int oldOrderQuantity = this.cartDetailService.findCartDetailById(id).get().getQuantity();

		if (quantity == -1) {

			this.cartDetailService.findCartDetailById(id).get().setQuantity(1);
			return ResponseEntity.ok(this.cartDetailService.findCartDetailById(id).get());
		}

		if (quantity < quantityProduct) {

			this.cartDetailService.findCartDetailById(id).get().setQuantity(quantity);
		} else {

			this.cartDetailService.findCartDetailById(id).get().setQuantity(quantityProduct);
		}

		return ResponseEntity.ok(this.cartDetailService.findCartDetailById(id).get());
	}

	@PostMapping()
	public ResponseEntity<CartDetail> post(@RequestBody CartDetail detail) {

		if (!this.cartService.checkCartExistedById(detail.getCart().getCartId())) {

			return ResponseEntity.notFound().build();
		}

//		boolean checkFlag = false;
		List<Product> listProducts = this.productService.findProductsByStatusTrue();
		Product product = this.productService.findProductByIdAndStatusTrue(detail.getProduct().getProductId());

		if (product == null) {

			return ResponseEntity.notFound().build();
		}

//		for (Product item : listProducts) {
//
//			if (item.getProductId() == product.getProductId()) {
//
//				checkFlag = true;
//				Product newProduct = this.productService.getProductById(product.getProductId()).get();
//				 if (newProduct.getQuantity() - 1 == 0) {
//
//					newProduct.setQuantity(0);
//					newProduct.setStatus(false);
//					this.productService.saveProduct(newProduct);
//				} else {
//
//					newProduct.setQuantity(newProduct.getQuantity() - 1);
//					this.productService.saveProduct(newProduct);
//				}
//			}
//		}
//		if (!checkFlag) {
//
//			return ResponseEntity.notFound().build();
//		}

		List<CartDetail> listCartsDetail = this.cartDetailService
				.findCartsDetailByCart(this.cartService.findCartById(detail.getCart().getCartId()).get());

		for (CartDetail item : listCartsDetail) {

			if (item.getProduct().getProductId() == detail.getProduct().getProductId()) {

				if (item.getQuantity() > this.productService.getProductById(detail.getProduct().getProductId()).get().getQuantity()) {

					return ResponseEntity.notFound().build();
				} else if (item.getQuantity() == this.productService.getProductById(detail.getProduct().getProductId()).get().getQuantity()) {

					return ResponseEntity.notFound().build();
				} else {

					item.setQuantity(item.getQuantity() + 1);
					item.setPrice(item.getPrice() + detail.getPrice());
					return ResponseEntity.ok(this.cartDetailService.saveCartDetail(item));
				}
			}
		}

		return ResponseEntity.ok(this.cartDetailService.saveCartDetail(detail));

	}

	@PutMapping()
	public ResponseEntity<CartDetail> put(@RequestBody CartDetail detail) {

		if (!this.cartService.checkCartExistedById(detail.getCart().getCartId())) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.cartDetailService.saveCartDetail(detail));

	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		if (!this.cartDetailService.checkCartDetailExistedById(id)) {

			return ResponseEntity.notFound().build();
		}
//		int currentOrderQuantity = this.cartDetailService.findCartDetailById(id).get().getQuantity();
//		int currentProductQuantity = this.cartDetailService.findCartDetailById(id).get().getProduct().getQuantity();
//
//		this.cartDetailService.findCartDetailById(id).get().getProduct().setQuantity(currentOrderQuantity + currentProductQuantity);
//		if (this.cartDetailService.findCartDetailById(id).get().getProduct().getQuantity() > 0) {
//
//			this.cartDetailService.findCartDetailById(id).get().getProduct().setStatus(true);
//		}

		this.cartDetailService.deleteSpecCartDetailById(id);
		return ResponseEntity.ok().build();

	}

}
