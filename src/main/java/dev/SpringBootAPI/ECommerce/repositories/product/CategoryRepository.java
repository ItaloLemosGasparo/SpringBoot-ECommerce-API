package dev.SpringBootAPI.ECommerce.repositories.product;

import dev.SpringBootAPI.ECommerce.models.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentCategoryIsNull();

    List<Category> findByParentCategory_id(Long id);
}
