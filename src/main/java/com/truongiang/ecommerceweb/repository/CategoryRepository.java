package com.truongiang.ecommerceweb.repository;

import com.truongiang.ecommerceweb.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}