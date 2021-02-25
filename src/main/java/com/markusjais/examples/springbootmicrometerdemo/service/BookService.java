package com.markusjais.examples.springbootmicrometerdemo.service;


import com.markusjais.examples.springbootmicrometerdemo.domain.Book;
import com.markusjais.examples.springbootmicrometerdemo.domain.events.BookCreationEvent;
import com.markusjais.examples.springbootmicrometerdemo.exceptions.ResourceNotFoundException;
import com.markusjais.examples.springbootmicrometerdemo.repository.BookRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final EventService eventService;
    private final MeterRegistry meterRegistry;


    public BookService(BookRepository bookRepository, EventService eventService,
                       MeterRegistry meterRegistry) {
        this.bookRepository = bookRepository;
        this.eventService = eventService;
        this.meterRegistry = meterRegistry;
    }


    public Book findBookById(Long bookId)
            throws ResourceNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));
        return book;
    }


    public List<Book> getAllBooks() {
        Timer.Sample sample = Timer.start(meterRegistry);
        List<Book> allBooks = bookRepository.findAll();
        sample.stop(meterRegistry.timer("getAllBooks.timer"));
        return allBooks;
    }

    public Book createBook(Book book) {
        Book createdBook = bookRepository.save(book);
        sendBookCreationEvent(createdBook);
        return createdBook;
    }

    private void sendBookCreationEvent(Book createdBook) {
        eventService.sendBookCreationEvent(new BookCreationEvent(createdBook.getBookId(), LocalDateTime.now()));
    }

    public void deleteBookById(Long bookId) {
        Timer timer = meterRegistry.timer("deleteBookById");
        timer.record(() -> {
            bookRepository.deleteById(bookId);
        });
    }
}