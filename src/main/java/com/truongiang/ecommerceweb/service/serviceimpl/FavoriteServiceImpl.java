package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.model.Favorite;
import com.truongiang.ecommerceweb.model.Product;
import com.truongiang.ecommerceweb.model.User;
import com.truongiang.ecommerceweb.repository.FavoriteRepository;
import com.truongiang.ecommerceweb.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Override
    public List<Favorite> findFavoritesByUser(User user) {

        return this.favoriteRepository.findByUser(user);

    }

    @Override
    public Integer countFavoritesByProduct(Product product) {

        return this.favoriteRepository.countByProduct(product);

    }

    @Override
    public Favorite findFavoritesByProductAndUser(Product product, User user) {

        return this.favoriteRepository.findByProductAndUser(product, user);

    }

    @Override
    public Favorite saveFavorite(Favorite favorite) {

        this.favoriteRepository.save(favorite);
        return favorite;

    }

    @Override
    public boolean checkExistedFavoriteById(Long id) {

        return this.favoriteRepository.existsById(id);

    }

    @Override
    public void deleteSpecFavoriteById(Long id) {

        this.favoriteRepository.deleteById(id);

    }

}
