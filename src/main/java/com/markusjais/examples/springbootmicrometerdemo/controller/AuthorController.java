package com.markusjais.examples.springbootmicrometerdemo.controller;


import com.markusjais.examples.springbootmicrometerdemo.domain.Book;
import com.markusjais.examples.springbootmicrometerdemo.exceptions.ResourceNotFoundException;
import com.markusjais.examples.springbootmicrometerdemo.service.BookService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
@Timed
public class AuthorController {

    private final BookService bookService;
    private final MeterRegistry meterRegistry;

    public AuthorController(BookService bookService, MeterRegistry meterRegistry) {
        this.bookService = bookService;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/{authorName}/books")
    @Timed(value = "getBooksByAuthorNameTimer")
    public Optional<List<Book>> getBookById(@PathVariable(value = "authorName") String authorName)
            throws ResourceNotFoundException {
        return bookService.findBooksByAuthorName(authorName);
    }

}

