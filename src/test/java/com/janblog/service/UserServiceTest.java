package com.janblog.service;

import com.janblog.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void shouldReturnAListOfAllUsers() { // Find a way to user an indepedent mongodb database for testing
        List<User> users = userService.findAll();
        assertThat(users).isNotNull();
    }
}