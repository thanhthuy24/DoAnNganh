package com.htt.ecourse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class ApiCategoryController {
    //hien thi tat ca category
    @GetMapping("/categories")
    public ResponseEntity<String> getAllCategories() {
        return ResponseEntity.ok("hiii");
    }
}
