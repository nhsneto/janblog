package com.janblog.controller;

import com.janblog.repository.UserPopulator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.data.mongodb.database = janblog-web-test"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private static UserPopulator populator;

    @BeforeAll
    public static void initialize() {
        populator = new UserPopulator("janblog-web-test", "localhost", "27017");
    }

    @BeforeEach
    public void populateDb() {
        populator.populate();
    }

    @AfterEach
    public void dropCollection() {
        populator.dropCollection();
    }

    @AfterAll
    public static void dropDb() {
        populator.dropDatabase();
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        String jsonContent = "[{\"id\":\"65135df93f90656e284ca8d8\",\"username\":\"stengerald\",\"email\":\"stengerald@email.com\",\"role\":\"usr\",\"createdAt\":\"2023-10-19T02:13:12.498Z\",\"updatedAt\":\"2023-10-19T02:13:12.498Z\"}," +
                "{\"id\":\"65135e1b3f90656e284ca8d9\",\"username\":\"ferdonanda\",\"email\":\"ferdonanda@email.com\",\"role\":\"usr\",\"createdAt\":\"2023-10-19T02:13:12.503Z\",\"updatedAt\":\"2023-10-19T02:13:12.503Z\"}," +
                "{\"id\":\"65135e273f90656e284ca8da\",\"username\":\"danilojosef\",\"email\":\"danilojosef@email.com\",\"role\":\"usr\",\"createdAt\":\"2023-10-19T02:13:12.508Z\",\"updatedAt\":\"2023-10-19T02:13:12.508Z\"}," +
                "{\"id\":\"65135e343f90656e284ca8db\",\"username\":\"romanlauren\",\"email\":\"romanlauren@email.com\",\"role\":\"usr\",\"createdAt\":\"2023-10-19T02:13:12.513Z\",\"updatedAt\":\"2023-10-19T02:13:12.513Z\"}," +
                "{\"id\":\"65135df93f90656e284ca8dc\",\"username\":\"branislavignas\",\"email\":\"branislavignas@email.com\",\"role\":\"usr\",\"createdAt\":\"2023-10-19T02:13:12.518Z\",\"updatedAt\":\"2023-10-19T02:13:12.518Z\"}]";

        mockMvc.perform(get("/janblog/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonContent));
    }
}
