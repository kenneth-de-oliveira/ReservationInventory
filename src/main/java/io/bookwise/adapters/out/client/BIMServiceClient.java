package io.bookwise.adapters.out.client;

import com.example.inventorymanagement.*;
import io.bookwise.adapters.out.client.dto.RetrieveAllBooksRequest;
import io.bookwise.framework.config.adapter.SoapAdapterConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "BIM-WS",
        url = "${api.inventory-management.url}",
        configuration = SoapAdapterConfig.class
)
public interface BIMServiceClient {

    /**
     * Finds a book by its ISBN.
     *
     * @param request the request containing the ISBN of the book to be searched
     * @return the response containing the book details
     */
    @PostMapping
    BookResponse findByIsbn(@RequestBody SearchBookRequest request);

    /**
     * Retrieves all books.
     *
     * @param request the request to retrieve all books
     * @return the response containing the list of all books
     */
    @PostMapping
    BookResponse findAll(@RequestBody RetrieveAllBooksRequest request);

    @PostMapping
    CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest);

    @PostMapping
    void createBook(@RequestBody BookRequest bookRequest);

}