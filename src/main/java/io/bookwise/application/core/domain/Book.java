package io.bookwise.application.core.domain;

public class Book {

    private Long id;
    private String title;
    private String authorName;
    private String text;
    private String isbn;
    private Category category;
    private Boolean reserved;

    public Book() {
    }

    public Book(String title, String authorName, String text, String isbn, Category category, boolean reserved) {
        this.title = title;
        this.authorName = authorName;
        this.text = text;
        this.isbn = isbn;
        this.category = category;
        this.reserved = reserved;
    }

    public void reserve() {
        if (!reserved) {
            setReserved(Boolean.TRUE);
        }
    }

    public boolean isReserved() {
        return reserved;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", text='" + text + '\'' +
                ", isbn='" + isbn + '\'' +
                ", category=" + category +
                ", reserved=" + reserved +
                '}';
    }

}