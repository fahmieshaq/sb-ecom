package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    //private List<Category> categories = new ArrayList<>();
    //private Long nextId = 1L;

    @Autowired
    private CategoryRespository categoryRespository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRespository.findAll();
        if (categories.isEmpty())
            throw new APIException("No categories created till now");

        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCat = categoryRespository.findByCategoryName(category.getCategoryName());
        if(savedCat != null)
            throw new APIException("Category " + category.getCategoryName() + " already exists");

        //category.setCategoryId(nextId++);
        categoryRespository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRespository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        categoryRespository.delete(category);
        return "Category with categoryId: " + categoryId + " deleted successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Category savedCategory = categoryRespository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));

        category.setCategoryId(categoryId);

        savedCategory = categoryRespository.save(category);

        return savedCategory;
    }
}
