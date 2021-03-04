package com.markusjais.examples.springbootmicrometerdemo.load;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markusjais.examples.springbootmicrometerdemo.domain.Book;
import com.markusjais.examples.springbootmicrometerdemo.util.RandomUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class BookHttpRequests {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var completableFutures = performManyGetSingleBookRequests(20);
        for(CompletableFuture<Book> future : completableFutures) {
            System.out.println(future.get());
        }
    }

    public static List<CompletableFuture<Book>> performManyGetSingleBookRequests(int numberOfRequests) {
        List<String> uris = new ArrayList<>();
        for(int i = 0; i < numberOfRequests; i++) {
            uris.add("http://localhost:8080/books/" + RandomUtil.INSTANCE.getRandomIntBetween(1, 7));
        }
        return uris.stream()
                .map(uri -> performGetSingleBookRequest(uri))
                .collect(Collectors.toList());
    }

    public static CompletableFuture<Book> performGetSingleBookRequest(String uri) {
        BookMapper objectMapper = new BookMapper();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(objectMapper::readBook);
    }

    static class BookMapper extends ObjectMapper {
        Book readBook(String content) {
            try {
                return this.readValue(content, new TypeReference<>() {
                });
            } catch (IOException ioe) {
                throw new CompletionException(ioe);
            }
        }
    }
}
