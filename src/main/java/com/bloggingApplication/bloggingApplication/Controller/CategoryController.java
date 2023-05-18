package com.bloggingApplication.bloggingApplication.Controller;

import com.bloggingApplication.bloggingApplication.Payloads.ApiResponse;
import com.bloggingApplication.bloggingApplication.Payloads.CategoryDto;
import com.bloggingApplication.bloggingApplication.Payloads.UserDto;
import com.bloggingApplication.bloggingApplication.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    public CategoryService categoryService;
     // post Category
     @PostMapping("/create")
     public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
//         CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
         return new ResponseEntity<>(categoryService.createCategory(categoryDto),HttpStatus.CREATED);
     }

    // put Category update
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer cateid) {
        CategoryDto updatecategoryDto = this.categoryService.updateCategory(categoryDto, cateid);
        return ResponseEntity.ok(updatecategoryDto);
    }

    // get CategoryByID SearchById
    @GetMapping("search/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable("categoryId")Integer cateid){
         CategoryDto getCategoryById = this.categoryService.getCategoryById(cateid);
         return ResponseEntity.ok(getCategoryById);
    }

    // get CategoryList SearchAll
    @GetMapping("/all")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> getAllCategory= this.categoryService.getAllCategory();
        return ResponseEntity.ok(getAllCategory);
    }
    //Delete CategoryById
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory (@PathVariable("categoryId") Integer cateid){
         this.categoryService.deleteCategory(cateid);
         return new ResponseEntity<ApiResponse>(new ApiResponse("Category Data Deleted Successfully",true),HttpStatus.OK);
    }
}
