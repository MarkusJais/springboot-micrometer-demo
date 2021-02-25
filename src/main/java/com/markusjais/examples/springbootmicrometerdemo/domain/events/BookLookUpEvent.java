package com.markusjais.examples.springbootmicrometerdemo.domain.events;

import java.time.LocalDateTime;

public record BookLookUpEvent(Long bookId, LocalDateTime localDateTime) implements BookEvent {
}
