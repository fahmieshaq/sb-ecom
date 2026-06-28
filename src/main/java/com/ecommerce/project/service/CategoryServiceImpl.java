package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        // Pagination section - start
        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // http://localhost:8080/api/public/categories?pageNumber=0&pageSize=10
        // http://localhost:8080/api/public/categories?pageNumber=1&pageSize=10&sortBy=categoryId&sortOrder=desc
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRespository.findAll(pageDetails);
        // Pagination section - end

        List<Category> categories = categoryPage.getContent(); //categoryRespository.findAll();
        if (categories.isEmpty())
            throw new APIException("No categories created till now");

        // For every category in the list, we are mapping that category
        // to CategoryDTO. We are using the concept of stream because
        // we are using categories in a list. Then for every category (map)
        // we are using modelMapper to convert category object to the type
        // CategoryDTO and then we are converting CateogryDTO to a list
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRespository.findByCategoryName(category.getCategoryName());
        if(categoryFromDb != null)
            throw new APIException("Category " + category.getCategoryName() + " already exists");

        //category.setCategoryId(nextId++);
        Category savedCat = categoryRespository.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCat, CategoryDTO.class);
        return savedCategoryDTO;
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRespository.findById(categoryId)
                .orElseThrow(() -> new APIException("Category " + categoryId + " does not exists"));
        categoryRespository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
        //return "Category with categoryId: " + categoryId + " deleted successfully !!";
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRespository.findById(categoryId)
                .orElseThrow(() -> new APIException("Category " + category.getCategoryName() + " does not exist")); //ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));

        category.setCategoryId(categoryId);
        Category savedCategory = categoryRespository.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }
}
