package io.bookwise.adapters.out.client;

import com.example.inventorymanagement.BookResponse;
import com.example.inventorymanagement.SearchBookRequest;

public interface InventoryManagementClient {

    BookResponse findByIsbn(SearchBookRequest request);

}