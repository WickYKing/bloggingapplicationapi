package com.bloggingApplication.bloggingApplication.Service.impl;

import com.bloggingApplication.bloggingApplication.Entity.Category;
import com.bloggingApplication.bloggingApplication.Exceptions.ResourceNotFoundException;
import com.bloggingApplication.bloggingApplication.Payloads.CategoryDto;
import com.bloggingApplication.bloggingApplication.Repository.CategoryRepo;
import com.bloggingApplication.bloggingApplication.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
        Category addedCategory = this.categoryRepo.save(category);
        return this.categoryToDto(addedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepo.save(category);

        CategoryDto categoryDto1 = this.categoryToDto(updatedCategory);
        return categoryDto1;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        return this.categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
      List<Category> categories= this.categoryRepo.findAll();
      List<CategoryDto> categoryDtos = categories.stream().map(category ->this.categoryToDto(category)).collect(Collectors.toList());
      return categoryDtos;
    }

    private Category dtoToCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription((categoryDto.getCategoryDescription()));
    return category;
    }

    public CategoryDto categoryToDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryTitle(category.getCategoryTitle());
        categoryDto.setCategoryDescription(category.getCategoryDescription());
        return categoryDto;
    }
}
