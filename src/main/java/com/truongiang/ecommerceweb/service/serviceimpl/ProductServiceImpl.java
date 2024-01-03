package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.model.Category;
import com.truongiang.ecommerceweb.model.Order;
import com.truongiang.ecommerceweb.model.OrderDetail;
import com.truongiang.ecommerceweb.model.Product;
import com.truongiang.ecommerceweb.repository.ProductRepository;
import com.truongiang.ecommerceweb.service.OrderDetailService;
import com.truongiang.ecommerceweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public List<Product> findProductsByStatusTrue() {

        return this.productRepository.findByStatusTrue();

    }

    @Override
    public Product findProductByIdAndStatusTrue(Long id) {

        return this.productRepository.findByProductIdAndStatusTrue(id);

    }

    @Override
    public List<Product> findProductsByStatusTrueOrderBySoldDesc() {

        return this.productRepository.findByStatusTrueOrderBySoldDesc();

    }

    @Override
    public List<Product> findTop10ProductsByOrderBySoldDesc() {

        return this.productRepository.findTop10ByOrderBySoldDesc();

    }

    @Override
    public List<Product> findProductsByStatusTrueOrderByEnteredDateDesc() {

        return this.productRepository.findByStatusTrueOrderByEnteredDateDesc();

    }

    @Override
    public List<Product> findProductsByStatusTrueOrderByQuantityDesc() {

        return this.productRepository.findByStatusTrueOrderByQuantityDesc();

    }

    @Override
    public List<Product> findProductsRated() {

        return this.productRepository.findProductRated();

    }

    @Override
    public List<Product> findProductsSuggest(Long categoryId1, Long productId, Long categoryId2, Long categoryId3) {

        return this.productRepository.findProductSuggest(categoryId1, productId, categoryId2, categoryId3);

    }

    @Override
    public List<Product> findProductsByCategory(Category category) {

        return this.productRepository.findByCategory(category);

    }

    @Override
    public boolean checkProductExistedById(Long id) {

        return this.productRepository.existsById(id);

    }

    @Override
    public Optional<Product> getProductById(Long id) {

        return this.productRepository.findById(id);

    }

    @Override
    public Product saveProduct(Product product) {

        this.productRepository.save(product);
        return product;

    }

    @Override
    public void updateProductByOrder(Order order) {

        List<OrderDetail> listOrderDetail = orderDetailService.findOrdersDetailByOrder(order);

        for (OrderDetail orderDetail : listOrderDetail) {

            Product product = this.getProductById(orderDetail.getProduct().getProductId()).get();
            if (product != null) {

//                product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
                product.setSold(product.getSold() + orderDetail.getQuantity());
                this.saveProduct(product);
            }
        }

    }

    @Override
    public List<Product> findProductsByName(String name) {

        return this.productRepository.findProductsByName(name);

    }

}
