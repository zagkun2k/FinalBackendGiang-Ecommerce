package com.truongiang.ecommerceweb.service;

import com.truongiang.ecommerceweb.model.Category;
import com.truongiang.ecommerceweb.model.Order;
import com.truongiang.ecommerceweb.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findProductsByStatusTrue();

    Product findProductByIdAndStatusTrue(Long id);

    List<Product> findProductsByStatusTrueOrderBySoldDesc();

    List<Product> findTop10ProductsByOrderBySoldDesc();

    List<Product> findProductsByStatusTrueOrderByEnteredDateDesc();

    List<Product> findProductsByStatusTrueOrderByQuantityDesc();

    List<Product> findProductsRated();

    List<Product> findProductsSuggest(Long categoryId1, Long productId, Long categoryId2, Long categoryId3);

    List<Product> findProductsByCategory(Category category);

    boolean checkProductExistedById(Long id);

    Optional<Product> getProductById(Long id);

    Product saveProduct(Product product);

    void updateProductByOrder(Order order);

    List<Product> findProductsByName(String name);
}
