package com.bloggingApplication.bloggingApplication.Service;

import com.bloggingApplication.bloggingApplication.Payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
     //Create
    CategoryDto createCategory(CategoryDto categoryDto);
    //Update
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    //Delete
    void deleteCategory(Integer categoryId);
    //get
    CategoryDto getCategoryById(Integer categoryId);
    //getAll
    List<CategoryDto> getAllCategory();
}
