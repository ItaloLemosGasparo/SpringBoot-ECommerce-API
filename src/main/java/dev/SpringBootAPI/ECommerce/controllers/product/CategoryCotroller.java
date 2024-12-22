package dev.SpringBootAPI.ECommerce.controllers.product;

import dev.SpringBootAPI.ECommerce.dtos.product.CategoryDTO;
import dev.SpringBootAPI.ECommerce.dtos.product.CategoryPathDTO;
import dev.SpringBootAPI.ECommerce.models.product.Category;
import dev.SpringBootAPI.ECommerce.services.product.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryCotroller {

    @Autowired
    private CategoryService categoryService;

    //CREATE
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }
    //

    //READ
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categoryDTOS = categoryService.getCategories();
        return categoryDTOS.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categoryDTOS);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId)
                .map(categoryDTO -> ResponseEntity.ok(categoryDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/path/{categoryId}")
    public ResponseEntity<List<CategoryPathDTO>> getCategoryPath(@PathVariable Long categoryId) {
        List<CategoryPathDTO> categoryPathDTO = categoryService.getCategoryPath(categoryId);
        return categoryPathDTO.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoryPathDTO);
    }
    //
}
