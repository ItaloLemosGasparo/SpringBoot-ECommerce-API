package dev.SpringBootAPI.ECommerce.services.product;

import dev.SpringBootAPI.ECommerce.dtos.product.CategoryDTO;
import dev.SpringBootAPI.ECommerce.dtos.product.CategoryPathDTO;
import dev.SpringBootAPI.ECommerce.mappers.product.CategoryMapper;
import dev.SpringBootAPI.ECommerce.mappers.product.CategoryPathMapper;
import dev.SpringBootAPI.ECommerce.models.product.Category;
import dev.SpringBootAPI.ECommerce.repositories.product.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired

    private CategoryPathMapper categoryPathMapper;

    //Create
    public CategoryDTO createCategory(@Valid Category category) {
        return categoryMapper.toDto(categoryRepository.save(category));
    }
    //

    //Read
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public Optional<CategoryDTO> findById(Long categoryId) {
        return categoryRepository.findById(categoryId).map(categoryMapper::toDto);
    }

    public Optional<List<CategoryPathDTO>> getCategoryPath(Long categoryId) {
        return categoryRepository.findById(categoryId).map(category -> getCategoryPathIterative(category));
    }
    //

    //UPDATE
    public CategoryDTO updateCategory(CategoryDTO updatedCategoryDTO) {
        Category category = categoryRepository.findById(updatedCategoryDTO.getId()).get();

        if (updatedCategoryDTO.getName() != null)
            category.setName(updatedCategoryDTO.getName());

        if (updatedCategoryDTO.getIconUrl() != null)
            category.setIconUrl(updatedCategoryDTO.getIconUrl());

        if (updatedCategoryDTO.getParentId() != null)
            category.setParentCategory(new Category(updatedCategoryDTO.getParentId()));

        return categoryMapper.toDto(categoryRepository.save(category));
    }
    //

    //DELETE
    public void deleteCategory(Long categoryId) {
        categoryRepository.saveAll(
                categoryRepository.findByParentCategory_id(categoryId).stream()
                        .peek(category -> category.setParentCategory(null))
                        .toList()
        );

        categoryRepository.deleteById(categoryId);
    }
    //

    //OTHERS
    private List<CategoryPathDTO> getCategoryPathIterative(Category category) {
        List<Category> categoryPath = new ArrayList<>();
        while (category != null) {
            categoryPath.add(0, category);
            category = category.getParentCategory();
        }
        return categoryPath.stream().map(categoryPathMapper::toDto).toList();
    }
    //
}
