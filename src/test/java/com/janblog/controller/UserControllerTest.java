package com.janblog.controller;

import com.janblog.repository.UserPopulator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    public void givinAnExistingId_shouldGetUser() throws Exception {
        String userId = "65135e1b3f90656e284ca8d9";
        String jsonContent = "{\"id\":\"65135e1b3f90656e284ca8d9\",\"username\":\"ferdonanda\",\"email\":\"ferdonanda@email.com\",\"role\":\"usr\",\"createdAt\":\"2023-10-19T02:13:12.503Z\",\"updatedAt\":\"2023-10-19T02:13:12.503Z\"}";

        mockMvc.perform(get("/janblog/v1/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonContent));
    }

    @Test
    public void shouldSaveUser() throws Exception {
        String userJson = "{\"username\":\"newuser\",\"password\":\"newuser1234\",\"email\":\"newuser@email.com\"}";

        mockMvc.perform(post("/janblog/v1/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.email").value("newuser@email.com"))
                .andExpect(jsonPath("$.role").value("usr"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty());
    }

    @Test
    public void shouldChangeUsersPassword() throws Exception {
        String userId = "65135e343f90656e284ca8db";
        String passwordJson = "{\"oldPassword\":\"123456\",\"newPassword\":\"romanlauren\"}";

        mockMvc.perform(post("/janblog/v1/users/" + userId + "/change-password")
                        .content(passwordJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldChangeUsersEmail() throws Exception {
        String userId = "65135df93f90656e284ca8d8";
        String newEmail = "stengerald_newemail@email.com";
        String emailJson = "{\"oldEmail\":\"stengerald@email.com\",\"newEmail\":\"" + newEmail + "\"}";

        mockMvc.perform(post("/janblog/v1/users/" + userId + "/change-email")
                        .content(emailJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/janblog/v1/users/" + userId))
                .andExpect(jsonPath("$.email").value(newEmail));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        String userId = "65135df93f90656e284ca8dc";

        mockMvc.perform(delete("/janblog/v1/users/" + userId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givingNonExistingId_shouldNotGetUser() throws Exception {
        String userId = "nonExistingId";

        mockMvc.perform(get("/janblog/v1/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors").value("User with id=" + userId + " was not found"));
    }

    @Test
    public void savingUser_withNoUsername_shouldFail() throws Exception {
        String userJson = "{\"password\":\"newuser1234\",\"email\":\"newuser@email.com\"}";

        mockMvc.perform(post("/janblog/v1/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors").value("Username is required"));
    }

    @Test
    public void savingUser_withBlankUsername_shouldFail() throws Exception {
        String userJson = "{\"username\":\"\",\"password\":\"newuser1234\",\"email\":\"newuser@email.com\"}";

        mockMvc.perform(post("/janblog/v1/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors",
                        containsInAnyOrder("Username must be between 6 and 30 characters long",
                                "Username must be alphanumeric and start with a letter")));
    }

    @Test
    public void savingUser_withUsernameStartingWithNumber_shouldFail() throws Exception {
        String userJson = "{\"username\":\"1johndoe\",\"password\":\"newuser1234\",\"email\":\"newuser@email.com\"}";

        mockMvc.perform(post("/janblog/v1/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors").value("Username must be alphanumeric and start with a letter"));
    }

    @Test
    public void savingUser_withUsernameHavingLessThan6Characters_shouldFail() throws Exception {
        String userJson = "{\"username\":\"john\",\"password\":\"newuser1234\",\"email\":\"newuser@email.com\"}";

        mockMvc.perform(post("/janblog/v1/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors").value("Username must be between 6 and 30 characters long"));
    }

    @Test
    public void savingUser_withUsernameHavingMoreThan30Characters_shouldFail() throws Exception {
        String userJson = "{\"username\":\"jjjjjjjjjjoooooooooohhhhhhhhhhn\",\"password\":\"newuser1234\",\"email\":\"newuser@email.com\"}";

        mockMvc.perform(post("/janblog/v1/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors").value("Username must be between 6 and 30 characters long"));
    }

    @Test
    public void savingUser_withNoPassword_shouldFail() throws Exception {
        String userJson = "{\"username\":\"johndoe\",\"email\":\"newuser@email.com\"}";

        mockMvc.perform(post("/janblog/v1/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors").value("Password is required"));
    }

    @Test
    public void savingUser_withPasswordHavingLessThan6Characters_shouldFail() throws Exception {
        String userJson = "{\"username\":\"johndoe\",\"password\":\"1234\",\"email\":\"newuser@email.com\"}";

        mockMvc.perform(post("/janblog/v1/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors").value("Password must be between 6 and 128 characters long"));
    }
}
