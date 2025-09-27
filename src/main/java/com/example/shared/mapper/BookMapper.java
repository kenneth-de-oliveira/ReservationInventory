package com.example.shared.mapper;

import com.example.application.core.domain.Book;
import com.example.shared.dto.BookRequest;
import com.example.shared.dto.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface BookMapper {

    @Mapping(source = "categoryRequest", target = "category")
    Book toDomain(BookRequest bookRequest);

    @Mapping(source = "category", target = "categoryResponse")
    BookResponse toResponse(Book book);

}