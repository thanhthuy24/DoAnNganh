package com.htt.ecourse.service;

import com.htt.ecourse.dtos.CategoryDTO;
import com.htt.ecourse.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCateById(Long id);
    Category createCategory(CategoryDTO categoryDTO);
    Category updateCategory(Long categoryId, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}
