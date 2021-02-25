package com.markusjais.examples.springbootmicrometerdemo.controller;


import com.markusjais.examples.springbootmicrometerdemo.domain.Book;
import com.markusjais.examples.springbootmicrometerdemo.exceptions.ResourceNotFoundException;
import com.markusjais.examples.springbootmicrometerdemo.service.BookService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        meterRegistry.counter("book_read_counter").increment();
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
        meterRegistry.counter("book_creation_counter").increment();
        Book createdBook = bookService.createBook(book);
        return  new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable(value = "bookId") Long bookId) {
        bookService.deleteBookById(bookId);
    }

//    @Bean
//    public MeterFilter renameGetAllBooksTimerMeterFilter() {
//        return MeterFilter.renameTag("getAllBooksTimer", "book.request", "book.request.all");
//    }

//    @Bean
//    public MeterFilter removeGetBookByIdTimerMeterFilter() {
//        return MeterFilter.renameTag("getAllBooksTimer", "book.request", "book.request.all");
//    }

}
