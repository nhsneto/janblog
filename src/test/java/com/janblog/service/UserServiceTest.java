package com.janblog.service;

import com.janblog.dto.UserDTO;
import com.janblog.repository.UserPopulator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"spring.data.mongodb.database = janblog-test"})
class UserServiceTest {

    @Autowired
    private UserService userService;
    private static UserPopulator populator;

    @BeforeAll
    public static void initialize() {
        populator = new UserPopulator("janblog-test", "localhost", "27017");
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
    public void shouldReturnAListOfAllUsers() {
        List<UserDTO> users = userService.findAll();
        assertThat(users).hasSize(populator.getNumberOfUsers());
    }

    @Test
    public void shouldReturnAUser_byGivingAnExistingID() {
        String existingId = "65135df93f90656e284ca8d8";
        UserDTO user = userService.findById(existingId);
        assertThat(user.id()).isEqualTo(existingId);
    }
}
