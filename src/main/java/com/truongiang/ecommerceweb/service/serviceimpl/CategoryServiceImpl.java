package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.model.Category;
import com.truongiang.ecommerceweb.repository.CategoryRepository;
import com.truongiang.ecommerceweb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllInCategory() {

        return this.categoryRepository.findAll();

    }

    @Override
    public boolean checkExistedCategoryById(Long id) {

        return this.categoryRepository.existsById(id);

    }

    @Override
    public Optional<Category> findCategoryById(Long id) {

        return this.categoryRepository.findById(id);

    }

    @Override
    public Category saveCategory(Category category) {

        this.categoryRepository.save(category);
        return category;

    }

    @Override
    public void deleteSpecCategoryById(Long id) {

        this.categoryRepository.deleteById(id);
    }

}
