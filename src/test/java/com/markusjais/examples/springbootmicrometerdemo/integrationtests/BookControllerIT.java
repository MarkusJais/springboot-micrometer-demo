package com.markusjais.examples.springbootmicrometerdemo.integrationtests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markusjais.examples.springbootmicrometerdemo.domain.Book;
import com.markusjais.examples.springbootmicrometerdemo.repository.BookRepository;
import com.markusjais.examples.springbootmicrometerdemo.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepositoryMock;

    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void givenBooks_whenGetBookById_thenReturnJsonWithBook()
            throws Exception {

        Book book = new Book(1l, "testbook_1", "test_author");

        given(bookRepositoryMock.findById(1l)).willReturn(Optional.of(book));

        ResultActions resultActions = mvc.perform(get("/books/{bookId}",
                "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId", is(1)))
                .andExpect(jsonPath("$.title", is(book.getTitle())));
    }

    @Test
    public void givenBookInput_whenCreateBook_thenReturnJsonWithBook()
            throws Exception {

        Book book = new Book(1l, "testbook_2", "test_author_2");
        given(bookRepositoryMock.save(book)).willReturn(book);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultActions = mvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId", is(1)))
                .andExpect(jsonPath("$.title", is(book.getTitle())));
    }
}
