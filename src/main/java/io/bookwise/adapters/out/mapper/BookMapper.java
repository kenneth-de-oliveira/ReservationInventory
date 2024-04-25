package io.bookwise.adapters.out.mapper;

import io.bookwise.adapters.out.repository.entity.BookEntity;
import io.bookwise.application.core.domain.Book;

public class BookMapper {

    public static Book toDomain(BookEntity bookEntity) {
        var book = new Book();
        book.setId(bookEntity.getId());
        book.setReserved(bookEntity.getReserved());
        book.setAuthorName(bookEntity.getAuthorName());
        book.setCategory(CategoryMapper.toDomain(bookEntity.getCategory()));
        book.setText(bookEntity.getText());
        book.setIsbn(bookEntity.getIsbn());
        book.setTitle(bookEntity.getTitle());
        return book;
    }

    public static BookEntity toEntity(Book book) {
        var bookEntity = new BookEntity();
        bookEntity.setReserved(book.getReserved());
        bookEntity.setAuthorName(book.getAuthorName());
        bookEntity.setCategory(CategoryMapper.toEntity(book.getCategory()));
        bookEntity.setText(book.getText());
        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setTitle(book.getTitle());
        return bookEntity;
    }

}