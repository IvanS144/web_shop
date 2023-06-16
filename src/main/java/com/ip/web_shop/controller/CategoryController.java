package com.ip.web_shop.controller;

import com.ip.web_shop.model.Category;
import com.ip.web_shop.model.dto.CategoryDTO;
import com.ip.web_shop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll()
    {
        List<CategoryDTO> categories = categoryService.get(CategoryDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(categories);

    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO){
        Category categoryRequest = modelMapper.map(categoryDTO, Category.class);
        CategoryDTO categoryReply = categoryService.add(categoryRequest, CategoryDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(categoryReply);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO){
        Category categoryRequest = modelMapper.map(categoryDTO, Category.class);
        CategoryDTO categoryReply = categoryService.update(categoryRequest, id, CategoryDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(categoryReply);
    }


}
