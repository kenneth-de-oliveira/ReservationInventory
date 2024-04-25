package io.bookwise.adapters.in;

import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.in.CreateBookPortIn;
import io.bookwise.application.core.ports.in.FindBookPortIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookResourceAdapterIn {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookResourceAdapterIn.class);

    private final CreateBookPortIn createBookPortIn;
    private final FindBookPortIn findBookPortIn;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        try {
            log.info("Creating book with books controller : {}", book);
            book = createBookPortIn.create(book);
            ResponseEntity<Book> responseEntity = ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{isbn}")
                    .buildAndExpand(book.getIsbn()).toUri()).body(book);
            log.info("Created book with books controller : {}", book);
            return responseEntity;
        }catch (Exception ex){
            LOGGER.error("Error creating book: {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @GetMapping
    public ResponseEntity<Book> find(@RequestParam String isbn) {
        log.info("Finding book with isbn with books controller: {}", isbn);
        var book = findBookPortIn.findIsbn(isbn);
        log.info("Found book with isbn with books controller: {}", book);
        return ResponseEntity.ok().body(book);
    }

}