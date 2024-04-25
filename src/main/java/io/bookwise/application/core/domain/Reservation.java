package io.bookwise.application.core.domain;

public class Reservation {

    private String isbn;
    private String document;

    public Reservation() {
    }

    public Reservation(String isbn, String document) {
        this.isbn = isbn;
        this.document = document;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "isbn='" + isbn + '\'' +
                ", document='" + document + '\'' +
                '}';
    }

}