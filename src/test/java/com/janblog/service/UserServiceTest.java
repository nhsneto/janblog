package com.janblog.service;

import com.janblog.dto.UserDTO;
import com.janblog.model.Role;
import com.janblog.model.User;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    private static MongoTemplate mongoTemplate;
    private static Logger LOG;

    @BeforeAll
    public static void initializeDb() {
        LOG = LoggerFactory.getLogger(UserServiceTest.class);
        LOG.info("Executing UserService tests");

        String dbName = "janblog-test";
        ConnectionString cs = new ConnectionString("mongodb://localhost:27017/" + dbName);
        mongoTemplate = new MongoTemplate(MongoClients.create(cs), dbName);
    }

    @BeforeEach
    public void populateDb() {
        int NUMBER_OF_USERS = 5;
        String[] names = {"stengerald", "ferdonanda", "danilojosef", "romanlauren", "branislavignas"};
        String[] emails = {"stengerald@email.com", "ferdonanda@email.com", "danilojosef@email.com",
                "romanlauren@email.com", "branislavignas@email.com"};

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            User user = new User();
            user.setUsername(names[i]);
            user.setEmail(emails[i]);
            user.setPassword("1234");
            user.setRole(Role.usr);
            user.setCreatedAt(Instant.now());
            user.setUpdatedAt(Instant.now());
            mongoTemplate.insert(user);
        }
    }

    // @AfterEach
    // public void dropCollection() {
    //     mongoTemplate.getCollection("users").drop();
    // }

    // @AfterAll
    // public static void dropDb() {
    //     LOG.info("Finished executing UserService tests");
    //     mongoTemplate.getDb().drop();
    // }

    @Test
    public void shouldReturnAListOfAllUsers() {
        List<UserDTO> users = userService.findAll();
        assertThat(users).isNotEmpty();
    }
    
    @Test
    public void shouldNotBeNull() {
        List<UserDTO> users = userService.findAll();
        assertThat(users).isNotNull();
    }
    
    @Test
    public void shouldContain5Users() {
        List<UserDTO> users = userService.findAll();
        assertThat(users).hasSize(5);
    }
}
