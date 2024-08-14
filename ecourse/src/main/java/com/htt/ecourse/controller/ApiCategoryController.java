package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.CategoryDTO;
import com.htt.ecourse.pojo.Category;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
//@Validated
public class ApiCategoryController {

    //hien thi tat ca category
    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok("get category");
    }

    //them category
    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
        BindingResult rs) {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("create category + " + categoryDTO);
    }

    @PutMapping("/{cateId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long cateId) {
        return ResponseEntity.ok("update category");
    }

    @DeleteMapping("/{cateId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long cateId) {
        return ResponseEntity.ok("delete category");
    }

}
