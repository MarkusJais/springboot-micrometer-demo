package com.markusjais.examples.springbootmicrometerdemo.repository;

import com.markusjais.examples.springbootmicrometerdemo.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void whenSaved_thenFindsByTitle() {
        Book canneryRow = new Book("Cannery Row", "John Steinbeck");
        bookRepository.save(canneryRow);

        Book storedEntity = bookRepository.findByTitle("Cannery Row");

        assertThat(storedEntity).isNotNull();
        assertThat(storedEntity.getAuthor()).isEqualTo("John Steinbeck");
        assertThat(storedEntity.getBookId()).isGreaterThan(0);
    }

    @Test
    void findByTitle() {
        Book book = bookRepository.findByTitle("These Truths");
        assertThat(book).isNotNull();
        assertThat(book.getAuthor()).isEqualTo("Jill Lepore");
    }
}
