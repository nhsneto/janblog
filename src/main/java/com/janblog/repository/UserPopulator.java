package com.janblog.repository;

import com.janblog.model.Role;
import com.janblog.model.User;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Instant;
import java.time.OffsetDateTime;

public class UserPopulator {

    private final MongoTemplate mongoTemplate;

    public UserPopulator(String dbName, String ip, String port) {
        ConnectionString cs = new ConnectionString("mongodb://" + ip + ":" + port + "/" + dbName);
        this.mongoTemplate = new MongoTemplate(MongoClients.create(cs), dbName);
    }

    public void populate() {
        mongoTemplate.save(new User("65135df93f90656e284ca8d8", "stengerald", "stengerald@email.com",
                "123456", Role.usr, Instant.parse("2023-10-19T02:13:12.498Z"), Instant.parse("2023-10-19T02:13:12.498Z")));
        mongoTemplate.save(new User("65135e1b3f90656e284ca8d9", "ferdonanda", "ferdonanda@email.com",
                "123456", Role.usr, Instant.parse("2023-10-19T02:13:12.503Z"), Instant.parse("2023-10-19T02:13:12.503Z")));
        mongoTemplate.save(new User("65135e273f90656e284ca8da", "danilojosef", "danilojosef@email.com",
                "123456", Role.usr, Instant.parse("2023-10-19T02:13:12.508Z"), Instant.parse("2023-10-19T02:13:12.508Z")));
        mongoTemplate.save(new User("65135e343f90656e284ca8db", "romanlauren", "romanlauren@email.com",
                "123456", Role.usr, Instant.parse("2023-10-19T02:13:12.513Z"), Instant.parse("2023-10-19T02:13:12.513Z")));
        mongoTemplate.save(new User("65135df93f90656e284ca8dc", "branislavignas", "branislavignas@email.com",
                "123456", Role.usr, Instant.parse("2023-10-19T02:13:12.518Z"), Instant.parse("2023-10-19T02:13:12.518Z")));
    }

    public void dropCollection() {
        mongoTemplate.dropCollection("users");
    }

    public void dropDatabase() {
        mongoTemplate.getDb().drop();
    }

    public int getNumberOfUsers() {
        long count = mongoTemplate.getCollection("users").countDocuments();
        return Math.toIntExact(count);
    }
}
