package com.example.shared.dto;

import lombok.Data;

@Data
public class BookResponse {
    private String title;
    private String authorName;
    private String text;
    private String isbn;
    private CategoryResponse categoryResponse;
    private Boolean reserved;
}