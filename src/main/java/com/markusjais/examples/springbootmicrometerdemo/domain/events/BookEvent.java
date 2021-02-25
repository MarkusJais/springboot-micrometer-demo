package com.markusjais.examples.springbootmicrometerdemo.domain.events;

public sealed interface BookEvent permits BookCreationEvent, BookLookUpEvent, BookDeletionEvent {
}
