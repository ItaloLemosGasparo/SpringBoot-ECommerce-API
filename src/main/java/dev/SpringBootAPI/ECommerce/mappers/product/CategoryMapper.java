package dev.SpringBootAPI.ECommerce.mappers.product;

import dev.SpringBootAPI.ECommerce.dtos.product.CategoryDTO;
import dev.SpringBootAPI.ECommerce.models.product.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "parentCategory.id", target = "parentId")
    CategoryDTO toDto(Category category);

    @Mapping(source = "parentId", target = "parentCategory.id")
    Category toEntity(CategoryDTO categoryDTO);
}
