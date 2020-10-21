package com.twitterinfocenter.controller;

import com.twitterinfocenter.model.Category;
import com.twitterinfocenter.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoriesController {

    private final CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public ResponseEntity<?> getCategories(@RequestParam List<String> categoryNames) {
        return ResponseEntity.ok(categoriesService.getCategories(categoryNames));
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoriesService.addCategory(category));
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoriesService.updateCategory(category));
    }
}
