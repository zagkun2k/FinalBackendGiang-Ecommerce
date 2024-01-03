package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.model.Category;
import com.truongiang.ecommerceweb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<Category>> getAll() {

		return ResponseEntity.ok(this.categoryService.getAllInCategory());

	}

	@GetMapping("{id}")
	public ResponseEntity<Category> getById(@PathVariable("id") Long id) {

		if (!this.categoryService.checkExistedCategoryById(id)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.categoryService.findCategoryById(id).get());

	}

	@PostMapping
	public ResponseEntity<Category> post(@RequestBody Category category) {

		if (this.categoryService.checkExistedCategoryById(category.getCategoryId())) {

			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(this.categoryService.saveCategory(category));

	}

	@PutMapping("{id}")
	public ResponseEntity<Category> put(@RequestBody Category category, @PathVariable("id") Long id) {

		if (!id.equals(category.getCategoryId())) {

			return ResponseEntity.badRequest().build();
		}

		if (!this.categoryService.checkExistedCategoryById(id)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.categoryService.saveCategory(category));

	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		if (!this.categoryService.checkExistedCategoryById(id)) {

			return ResponseEntity.notFound().build();
		}

		this.categoryService.deleteSpecCategoryById(id);
		return ResponseEntity.ok().build();

	}

}
