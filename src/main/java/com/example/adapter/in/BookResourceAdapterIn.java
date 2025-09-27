package com.example.adapter.in;

import com.example.application.core.port.in.CreateBookPortIn;
import com.example.application.core.port.in.FindBookPortIn;
import com.example.shared.dto.BookRequest;
import com.example.shared.dto.BookResponse;
import com.example.shared.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookResourceAdapterIn {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookResourceAdapterIn.class);

    private final CreateBookPortIn createBookPortIn;
    private final FindBookPortIn findBookPortIn;
    private final BookMapper mapper;

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAll() {
        log.info("Finding all books with books controller");
        var books = findBookPortIn.findAll();
        var list = books.stream()
                .map(mapper::toResponse)
                .toList();
        log.info("Found all books with books controller");
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody BookRequest bookRequest) {
        try {
            log.info("Creating book with books controller : {}", bookRequest);
            var book = mapper.toDomain(bookRequest);
            createBookPortIn.create(book);
            ResponseEntity<Void> responseEntity = ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{isbn}")
                    .buildAndExpand(book.getIsbn()).toUri()).build();
            log.info("Created book with books controller : {}", book);
            return responseEntity;
        } catch (Exception ex) {
            LOGGER.error("Error creating book: {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @GetMapping(value = "/{isbn}")
    public ResponseEntity<BookResponse> findIsbn(@PathVariable String isbn) {
        log.info("Finding book with isbn with books controller: {}", isbn);
        var book = findBookPortIn.findIsbn(isbn);
        var bookResponse = mapper.toResponse(book);
        log.info("Found book with isbn with books controller: {}", bookResponse);
        return ResponseEntity.ok().body(bookResponse);
    }

}