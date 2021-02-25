package com.markusjais.examples.springbootmicrometerdemo.service;


import com.markusjais.examples.springbootmicrometerdemo.domain.events.BookCreationEvent;
import com.markusjais.examples.springbootmicrometerdemo.domain.events.BookDeletionEvent;
import com.markusjais.examples.springbootmicrometerdemo.domain.events.BookLookUpEvent;
import com.markusjais.examples.springbootmicrometerdemo.exceptions.ResourceNotFoundException;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    private final MeterRegistry meterRegistry;


    public EventService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }


    public void sendBookCreationEvent(BookCreationEvent bookCreationEvent) {
        ;
    }

    public void sendBookLookUpEvent(BookLookUpEvent bookLookUpEvent) {
        ;
    }

    public void sendBookDeletionEvent(BookDeletionEvent bookDeletionEvent) {
        ;
    }

}