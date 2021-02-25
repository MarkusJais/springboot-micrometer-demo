package com.markusjais.examples.springbootmicrometerdemo.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Immutable
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

   public Book(Long bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }


    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    private Book() {
    }


    public Long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId) && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

/*
    CREATE TABLE books (
        book_id              SERIAL PRIMARY KEY,
        title           VARCHAR(100) NOT NULL,
        author  VARCHAR(100) NOT NULL
);

http -v POST http://localhost:8080/books author='Ian Newton' title='Upland Birds'


*/
