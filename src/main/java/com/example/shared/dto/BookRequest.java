package com.example.shared.dto;

import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private String authorName;
    private String text;
    private String isbn;
    private CategoryRequest categoryRequest;
}