package io.bookwise.adapters.out.mapper;

import com.example.inventorymanagement.*;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.domain.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class InventoryManagementMapper {

    public SearchBookRequest mapToSearchBookRequest(String isbn) {
        return Optional.ofNullable(isbn).map(
                isbnValue -> {
                    var searchBookRequest = new SearchBookRequest();
                    var searchBook = new SearchBook();
                    searchBook.setIsbn(isbnValue);
                    searchBookRequest.setBook(searchBook);
                    return searchBookRequest;
                }
        ).orElse(null);

    }

    public List<Book> mapToBookDomainList(BookResponse bookResponse) {
        return Objects.requireNonNull(Optional.ofNullable(bookResponse)
                .map(BookResponse::getBook)
                .map(books -> books.stream().map(this::convertToBookDomain))
                .orElse(null)).toList();
    }

    public Book mapToBookDomain(BookResponse bookResponse) {
        return Optional.ofNullable(bookResponse)
                .flatMap(response -> response.getBook().stream().findFirst())
                .map(this::convertToBookDomain)
                .orElse(null);
    }

    public CategoryRequest mapToCategoryRequest(Category category) {
        return Optional.ofNullable(category).map(
                categoryDomain -> {
                    var categoryRequest = new CategoryRequest();
                    categoryRequest.setCategory(convertToSearchCategory(category));
                    return categoryRequest;
                }
        ).orElse(null);
    }

    public BookRequest mapToBookRequest(Book book) {
        return Optional.ofNullable(book).map(
                bookDomain -> {
                    var bookRequest = new BookRequest();
                    bookRequest.setBook(toBook(bookDomain));
                    return bookRequest;
                }
        ).orElse(null);
    }

    private com.example.inventorymanagement.Book toBook(Book bookDomain) {
        var book = new com.example.inventorymanagement.Book();
        book.setIsbn(bookDomain.getIsbn());
        book.setTitle(bookDomain.getTitle());
        book.setAuthorName(bookDomain.getAuthorName());
        book.setText(bookDomain.getText());
        book.setCategoryId(bookDomain.getId());
        return book;
    }

    public Category mapToCategoryDomain(CategoryResponse categoryResponse) {
        Optional<SearchCategory> first = categoryResponse.getCategory().stream().findFirst();
        return first.map(this::convertToCategoryDomain).orElse(null);
    }

    private SearchCategory convertToSearchCategory(Category category) {
       return Optional.ofNullable(category).map(
                categoryDomain -> {
                    var searchCategory = new SearchCategory();
                    searchCategory.setName(categoryDomain.getName());
                    searchCategory.setDescription(categoryDomain.getDescription());
                    return searchCategory;
                }
        ).orElse(null);
    }

    private Category convertToCategoryDomain(SearchCategory searchCategory) {
        var category = new Category();
        category.setId(searchCategory.getId());
        category.setName(searchCategory.getName());
        category.setDescription(searchCategory.getDescription());
        return category;
    }

    private Book convertToBookDomain(SearchBook searchBook) {
        Category category = toCategory(searchBook);
        return toBookDomain(searchBook, category);
    }

    private Book toBookDomain(SearchBook book, Category category) {
        var bookDomain = new Book();
        bookDomain.setIsbn(book.getIsbn());
        bookDomain.setId(book.getId());
        bookDomain.setTitle(book.getTitle());
        bookDomain.setAuthorName(book.getAuthorName());
        bookDomain.setText(book.getText());
        bookDomain.setCategory(category);
        return bookDomain;
    }

    private Category toCategory(SearchBook book) {
        var searchCategory = book.getCategory();
        var category = new Category();
        category.setId(searchCategory.getId());
        category.setName(searchCategory.getName());
        category.setDescription(searchCategory.getDescription());
        return category;
    }

}