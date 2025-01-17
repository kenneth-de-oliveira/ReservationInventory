package io.bookwise.adapters.out.client;

import com.example.inventorymanagement.BookResponse;
import com.example.inventorymanagement.SearchBookRequest;
import io.bookwise.framework.config.adapter.SoapAdapterConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * BIMClient is a Feign client interface for interacting with the Inventory Management Web Service (BIM-WS).
 * It provides methods to perform operations related to books, such as finding a book by its ISBN.
 *
 * <p>This client is configured to communicate with the BIM-WS service located at the specified URL.
 * The configuration for this client is provided by the {@link io.bookwise.framework.config.adapter.SoapAdapterConfig} class.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * @Autowired
 * private BIMServiceClient serviceClient;
 *
 * SearchBookRequest request = new SearchBookRequest();
 * // set request parameters
 * BookResponse response = serviceClient.findByIsbn(request);
 * }</pre>
 *
 * @see io.bookwise.framework.config.adapter.SoapAdapterConfig
 * @author koliveiras
 */
@FeignClient(
        name = "BIM-WS",
        url = "http://localhost:8080/BIM-WS/InventoryManagementWS",
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

}