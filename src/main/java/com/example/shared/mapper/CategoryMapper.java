package com.example.shared.mapper;

import com.example.shared.dto.CategoryRequest;
import com.example.application.core.domain.Category;
import com.example.shared.dto.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toDomain(CategoryRequest categoryRequest);

    Category toDomain(CategoryResponse categoryResponse);

}