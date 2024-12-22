package dev.SpringBootAPI.ECommerce.repositories.product;

import dev.SpringBootAPI.ECommerce.models.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value =
            "WITH RECURSIVE category_path AS ( " +
                    "    SELECT id, name, previous_category_id " +
                    "    FROM categories " +
                    "    WHERE id = :categoryId " +
                    "    UNION ALL " +
                    "    SELECT c.id, c.name, c.previous_category_id " +
                    "    FROM categories c " +
                    "    INNER JOIN category_path cp ON cp.previous_category_id = c.id " +
                    ") " +
                    "SELECT id, name, previous_category_id " +
                    "FROM category_path", nativeQuery = true)
    List<Object[]> findCategoryPath(@Param("categoryId") Long categoryId);

    List<Category> findByParentCategoryIsNull();

    List<Category> findByParentCategoryId(Long parentCategoryId);
}
