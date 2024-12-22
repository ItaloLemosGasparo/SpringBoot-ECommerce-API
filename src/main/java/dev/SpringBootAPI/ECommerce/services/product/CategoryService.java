package dev.SpringBootAPI.ECommerce.services.product;

import dev.SpringBootAPI.ECommerce.dtos.product.CategoryDTO;
import dev.SpringBootAPI.ECommerce.dtos.product.CategoryPathDTO;
import dev.SpringBootAPI.ECommerce.mappers.product.CategoryMapper;
import dev.SpringBootAPI.ECommerce.mappers.product.CategoryPathMapper;
import dev.SpringBootAPI.ECommerce.models.product.Category;
import dev.SpringBootAPI.ECommerce.repositories.product.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    //

    public List<CategoryDTO> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public List<CategoryDTO> getTopCategories() {
        return categoryRepository.findByParentCategoryIsNull().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public List<CategoryPathDTO> getCategoryPath(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        return optionalCategory.map(category -> getCategoryPathIterative(category)).orElse(null);
    }

    private List<CategoryPathDTO> getCategoryPathIterative(Category category) {
        List<Category> categoryPath = new ArrayList<>();
        while (category != null) {
            categoryPath.add(0, category); // Insere no início para construir o caminho da raiz à folha
            category = category.getParentCategory();
        }
        return categoryPath.stream().map(categoryPathMapper::toDto).toList();
    }


    public Optional<CategoryDTO> getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).map(categoryMapper::toDto);
    }
}
