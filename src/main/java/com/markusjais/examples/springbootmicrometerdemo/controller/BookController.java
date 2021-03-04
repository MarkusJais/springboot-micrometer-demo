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
@RequestMapping("/books")
@Timed
public class BookController {

    private final BookService bookService;

    private final MeterRegistry meterRegistry;

    public BookController(BookService bookService, MeterRegistry meterRegistry) {
        this.bookService = bookService;
        this.meterRegistry = meterRegistry;
    }


    @GetMapping("/{bookId}")
    @Timed(value = "getBookByIdTimer")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "bookId") Long bookId)
            throws ResourceNotFoundException {
        meterRegistry.counter("book.getbyid.counter").increment();
        var book = bookService.findBookById(bookId);
        return ResponseEntity.ok().body(book);
    }


    @GetMapping
    @Timed(value = "getAllBooksTimer", extraTags = {"book.request", "all"})
    public List<Book> getAllBooks() {
        var allBooks = bookService.getAllBooks();
        return allBooks;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        meterRegistry.counter("book.creation.counter").increment();
        Book createdBook = bookService.createBook(book);
        return  new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable(value = "bookId") Long bookId) {
        bookService.deleteBookById(bookId);
    }
}

