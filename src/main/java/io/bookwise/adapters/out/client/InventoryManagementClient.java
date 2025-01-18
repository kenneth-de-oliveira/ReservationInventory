package io.bookwise.adapters.out.client;

import com.example.inventorymanagement.*;

public interface InventoryManagementClient {
    BookResponse findByIsbn(SearchBookRequest request);
    BookResponse findAll();
    CategoryResponse saveCategory(CategoryRequest categoryRequest);
    void saveBook(BookRequest bookRequest);
}