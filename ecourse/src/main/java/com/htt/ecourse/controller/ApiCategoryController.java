package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.CategoryDTO;
import com.htt.ecourse.pojo.Category;
import com.htt.ecourse.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class ApiCategoryController {
    private final CategoryService categoryService;
    //hien thi tat ca category
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page")     int page,
            @RequestParam("limit")    int limit
    ) {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    //them category
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
        BindingResult rs) {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("insert category successfully!");
    }

    @PutMapping("/{cateId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateCategory(
            @PathVariable Long cateId,
            @Valid @RequestBody CategoryDTO categoryDTO)
    {
        categoryService.updateCategory(cateId, categoryDTO);
        return ResponseEntity.ok("update category successfully!");
    }

    @DeleteMapping("/{cateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteCategory(@PathVariable Long cateId) {
        categoryService.deleteCategory(cateId);
        return ResponseEntity.ok("delete category successfully!");
    }

}
