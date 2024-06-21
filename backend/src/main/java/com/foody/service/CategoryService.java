package com.foody.service;

import com.foody.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(String name, Long userId) throws Exception;
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception;
    public Category findCategoryById(Long id) throws Exception;
}
