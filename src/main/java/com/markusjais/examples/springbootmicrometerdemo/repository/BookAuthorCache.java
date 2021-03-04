package com.markusjais.examples.springbootmicrometerdemo.repository;

import com.markusjais.examples.springbootmicrometerdemo.domain.Book;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Dummy cache implementation to show use of micrometer gauges.
 * <p>
 * THIS IS NOT A VERY EFFICIENT CACHE. DO NOT USE IN PRODUCTION.
 * In this cache, loading the data from the DB is probably faster than this cache!
 * There are much more efficient implementations available, e.g. from Guava
 */
@Component
@EnableScheduling
public class BookAuthorCache {


    private BookRepository bookRepository;
    private MeterRegistry meterRegistry;
    private Map<String, List<Book>> authorBookMap;

    public BookAuthorCache(BookRepository bookRepository, MeterRegistry meterRegistry) {
        this.bookRepository = bookRepository;
        this.meterRegistry = meterRegistry;
    }

    public synchronized Optional<List<Book>> getBookByAuthor(String authorName) {
        if (authorBookMap == null) {
            loadCacheFromDB();
        }
        meterRegistry.gauge("book.author.cache.size", authorBookMap.size());
        if (authorBookMap.containsKey(authorName)) {
            return Optional.of(authorBookMap.get(authorName));
        } else {
            return Optional.empty();
        }
    }

    @Scheduled(fixedDelay=500000)
    private void loadCacheFromDB() {
        System.out.println("CCC________________");
        authorBookMap = bookRepository.findAll().stream().collect(
                Collectors.groupingBy(Book::getAuthor, HashMap::new, Collectors.toList()));
        System.out.println(authorBookMap);
        System.out.println("==========================0");
    }

    public synchronized void invalidateCache() {
        authorBookMap = null;
    }



}
