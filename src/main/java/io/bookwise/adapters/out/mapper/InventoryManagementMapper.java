package io.bookwise.adapters.out.mapper;

import com.example.inventorymanagement.*;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface InventoryManagementMapper {

    @Mapping(target = "book.isbn", source = "isbn")
    SearchBookRequest toSearchBookRequest(String isbn);

    @Named("convertToBookDomain")
    default Book convertToBookDomain(SearchBook searchBook) {
        return Optional.ofNullable(searchBook)
                .map(bookValue -> toBookDomain(bookValue, toCategory(bookValue)))
                .orElse(null);
    }

    default List<Book> toBookDomainList(BookResponse bookResponse) {
        return Optional.ofNullable(bookResponse)
                .map(BookResponse::getBook)
                .map(books -> books.stream()
                        .map(this::convertToBookDomain)
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    default Book toBookDomain(BookResponse bookResponse) {
        return Optional.ofNullable(bookResponse)
                .map(BookResponse::getBook)
                .flatMap(books -> books.stream()
                        .map(this::convertToBookDomain)
                        .findFirst())
                .orElse(null);
    }

    default CategoryRequest toCategoryRequest(Category category) {
        return Optional.ofNullable(category)
                .map(this::convertToSearchCategory)
                .map(searchCategory -> {
                    var categoryRequest = new CategoryRequest();
                    categoryRequest.setCategory(searchCategory);
                    return categoryRequest;
                })
                .orElse(null);
    }

    default BookRequest toBookRequest(Book book) {
        return Optional.ofNullable(book)
                .map(this::toBook)
                .map(bookValue -> {
                    var bookRequest = new BookRequest();
                    bookRequest.setBook(bookValue);
                    return bookRequest;
                })
                .orElse(null);
    }

    default com.example.inventorymanagement.Book toBook(Book bookDomain) {
        return Optional.ofNullable(bookDomain)
                .map(bookValue -> {
                    var book = new com.example.inventorymanagement.Book();
                    book.setIsbn(bookValue.getIsbn());
                    book.setTitle(bookValue.getTitle());
                    book.setAuthorName(bookValue.getAuthorName());
                    book.setText(bookValue.getText());
                    book.setCategoryId(bookValue.getId());
                    return book;
                })
                .orElse(null);
    }

    default Category toCategoryDomain(CategoryResponse categoryResponse) {
        return Optional.ofNullable(categoryResponse)
                .map(CategoryResponse::getCategory)
                .flatMap(categories -> categories.stream()
                        .map(this::convertToCategoryDomain)
                        .findFirst())
                .orElse(null);
    }

    default SearchCategory convertToSearchCategory(Category category) {
        return Optional.ofNullable(category)
                .map(categoryValue -> {
                    var searchCategory = new SearchCategory();
                    searchCategory.setName(categoryValue.getName());
                    searchCategory.setDescription(categoryValue.getDescription());
                    return searchCategory;
                })
                .orElse(null);
    }

    default Category convertToCategoryDomain(SearchCategory searchCategory) {
        return Optional.ofNullable(searchCategory)
                .map(categoryValue -> {
                    var category = new Category();
                    category.setId(categoryValue.getId());
                    category.setName(categoryValue.getName());
                    category.setDescription(categoryValue.getDescription());
                    return category;
                })
                .orElse(null);
    }

    default Book toBookDomain(SearchBook book, Category category) {
        return Optional.ofNullable(book)
                .map(bookValue -> {
                    var bookDomain = new Book();
                    bookDomain.setIsbn(bookValue.getIsbn());
                    bookDomain.setId(bookValue.getId());
                    bookDomain.setTitle(bookValue.getTitle());
                    bookDomain.setAuthorName(bookValue.getAuthorName());
                    bookDomain.setText(bookValue.getText());
                    bookDomain.setCategory(category);
                    return bookDomain;
                })
                .orElse(null);
    }

    default Category toCategory(SearchBook book) {
        return Optional.ofNullable(book)
                .map(SearchBook::getCategory)
                .map(categoryValue -> {
                    var category = new Category();
                    category.setId(categoryValue.getId());
                    category.setName(categoryValue.getName());
                    category.setDescription(categoryValue.getDescription());
                    return category;
                })
                .orElse(null);
    }

}