package com.markusjais.examples.springbootmicrometerdemo.repository;

import com.markusjais.examples.springbootmicrometerdemo.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

    Book findByTitle(String title);


}