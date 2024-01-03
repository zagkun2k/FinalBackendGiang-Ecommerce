package com.truongiang.ecommerceweb.service;

import com.truongiang.ecommerceweb.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllInCategory();

    boolean checkExistedCategoryById(Long id);

    Optional<Category> findCategoryById(Long id);

    Category saveCategory(Category category);

    void deleteSpecCategoryById(Long id);

}
