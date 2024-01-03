package com.truongiang.ecommerceweb.repository;

import com.truongiang.ecommerceweb.model.Favorite;
import com.truongiang.ecommerceweb.model.Product;
import com.truongiang.ecommerceweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

	List<Favorite> findByUser(User user);

	Integer countByProduct(Product product);

	Favorite findByProductAndUser(Product product, User user);

}
