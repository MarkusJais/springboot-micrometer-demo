package com.markusjais.examples.springbootmicrometerdemo.service;

import com.markusjais.examples.springbootmicrometerdemo.domain.Book;
import com.markusjais.examples.springbootmicrometerdemo.repository.BookAuthorCache;
import com.markusjais.examples.springbootmicrometerdemo.repository.BookRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private BookRepository bookRepository = Mockito.mock(BookRepository.class);
    private EventService eventService = Mockito.mock(EventService.class);

    private BookService bookService;
    private MeterRegistry meterRegistry;
    private BookAuthorCache bookAuthorCache;

    @BeforeEach
    public void setup() {
        meterRegistry = new SimpleMeterRegistry();
        meterRegistry.clear();
    }

    @BeforeEach
    void initUseCase() {
        bookService = new BookService(bookRepository, eventService, meterRegistry, bookAuthorCache);
    }

    @Test
    void savedBookHasId() {
        Book book = new Book("testbook_1", "test_author");
        Book savedBook = new Book(1l, "testbook_1", "test_author");
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        Book createdBook = bookService.createBook(book);
        assertThat(createdBook.getBookId()).isGreaterThan(0);
    }

    @Test
    void getAllBooksHasTimerMetric() {
        Book book1 = new Book("testbook_1", "test_author_1");
        Book book2 = new Book("testbook_2", "test_author_2");
        List<Book> bookList = List.of(book1, book2);
        when(bookRepository.findAll()).thenReturn(bookList);
        List<Book> allBooksReturned = bookService.getAllBooks();
        assertThat(bookList.size()).isEqualTo(allBooksReturned.size());

        var requiredSearch = meterRegistry.get("getAllBooks.timer");
        Timer timer = requiredSearch.timer();

        assertThat(timer.count()).isEqualTo(1);
    }
}
