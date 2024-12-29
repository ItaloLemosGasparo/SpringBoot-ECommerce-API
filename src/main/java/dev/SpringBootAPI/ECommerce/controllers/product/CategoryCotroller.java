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
import java.util.Optional;

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
        List<CategoryDTO> categoryDTOS = categoryService.findAll();
        return categoryDTOS.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categoryDTOS);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long categoryId) {
        return categoryService.findById(categoryId)
                .map(categoryDTO -> ResponseEntity.ok(categoryDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/path/{categoryId}")
    public ResponseEntity<List<CategoryPathDTO>> getCategoryPath(@PathVariable Long categoryId) {
        // orElse always evaluates the fallback, even if not needed; orElseGet evaluates only if needed.
        return categoryService.getCategoryPath(categoryId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //

    //UPDATE
    @PutMapping
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO updatedcategoryDTO) {
        return categoryService.findById(updatedcategoryDTO.getId())
                .map(categoryDTO -> ResponseEntity.ok(categoryService.updateCategory(updatedcategoryDTO)))
                .orElse(ResponseEntity.notFound().build());
    }
    //

    //DELETE
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        Optional<CategoryDTO> categoryDTO = categoryService.findById(categoryId);

        if (categoryDTO.isEmpty())
            return ResponseEntity.notFound().build();

        categoryService.deleteCategory(categoryDTO.get().getId());
        return ResponseEntity.ok().build();
    }
    //
}
