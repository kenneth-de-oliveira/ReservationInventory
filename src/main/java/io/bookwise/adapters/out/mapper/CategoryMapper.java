package io.bookwise.adapters.out.mapper;

import io.bookwise.adapters.out.repository.entity.CategoryEntity;
import io.bookwise.application.core.domain.Category;

public class CategoryMapper {

    public static Category toDomain(CategoryEntity categoryEntity) {
        var category = new Category();
        category.setId(categoryEntity.getId());
        category.setName(categoryEntity.getName());
        category.setDescription(categoryEntity.getDescription());
        return category;
    }

    public static CategoryEntity toEntity(Category category) {
        var categoryEntity = new CategoryEntity();
        categoryEntity.setName(category.getName());
        categoryEntity.setDescription(category.getDescription());
        return categoryEntity;
    }

}