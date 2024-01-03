package com.truongiang.ecommerceweb.service;

import com.truongiang.ecommerceweb.model.Favorite;
import com.truongiang.ecommerceweb.model.Product;
import com.truongiang.ecommerceweb.model.User;

import java.util.List;

public interface FavoriteService {

    List<Favorite> findFavoritesByUser(User user);

    Integer countFavoritesByProduct(Product product);

    Favorite findFavoritesByProductAndUser(Product product, User user);

    Favorite saveFavorite(Favorite favorite);

    boolean checkExistedFavoriteById(Long id);

    void deleteSpecFavoriteById(Long id);

}
