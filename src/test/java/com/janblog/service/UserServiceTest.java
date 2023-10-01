package com.janblog.service;

import com.janblog.dto.EmailDTO;
import com.janblog.dto.PasswordDTO;
import com.janblog.dto.UserDTO;
import com.janblog.exception.UserException;
import com.janblog.model.Role;
import com.janblog.repository.UserPopulator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
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
    public void shouldReturnUser_byGivingExistingID() {
        String existingId = "65135df93f90656e284ca8d8";
        UserDTO user = userService.findById(existingId);
        assertThat(user.id()).isEqualTo(existingId);
    }

    @Test
    public void shouldSaveUser() {
        UserDTO user = new UserDTO(null, "newuser", "newuser@email.com", "newuser1234",
                Role.usr, Instant.now(), Instant.now());
        UserDTO savedUser = userService.save(user);
        assertThat(savedUser.id()).isNotNull();
    }

    @Test
    public void shouldChangeUsersPassword() {
        String userId = "65135df93f90656e284ca8dc";
        UserDTO user = userService.findById(userId);

        String oldPassword = user.password();
        String newPassword = "newpassword";
        PasswordDTO dto = new PasswordDTO(oldPassword, newPassword);

        userService.changePassword(userId, dto);

        UserDTO userWithChangedPassword = userService.findById(userId);
        assertThat(userWithChangedPassword.password()).isNotEqualTo(oldPassword);
    }

    @Test
    public void shouldChangeUsersEmail() {
        String userId = "65135e1b3f90656e284ca8d9";
        UserDTO user = userService.findById(userId);

        String oldEmail = user.email();
        String newEmail = "newemail@email.com";
        EmailDTO dto = new EmailDTO(oldEmail, newEmail);

        userService.changeEmail(userId, dto);

        UserDTO userWithChangedEmail = userService.findById(userId);
        assertThat(userWithChangedEmail.email()).isNotEqualTo(oldEmail);
    }

    @Test
    public void shouldDeleteUser_byGivingAnExistingId() {
        String userId = "65135e343f90656e284ca8db";
        userService.deleteById(userId);
        assertThatExceptionOfType(UserException.class)
                .isThrownBy(() -> userService.findById(userId))
                .withMessage("User with id=" + userId + " was not found");
    }

    @Test
    public void shouldFailWhenFindingUserByNotExistingId() {
        String userId = "not_existing_id";
        assertThatExceptionOfType(UserException.class)
                .isThrownBy(() -> userService.findById(userId))
                .withMessage("User with id=" + userId + " was not found");
    }
}
