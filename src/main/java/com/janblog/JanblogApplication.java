package com.janblog;

import com.janblog.model.Role;
import com.janblog.model.User;
import com.janblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class JanblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(JanblogApplication.class, args);
    }

    @Autowired
    public UserRepository repo;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (repo.findAll().isEmpty()) {
                User user = new User();
                user.setUsername("John Doe");
                user.setEmail("john@email.com");
                user.setPassword("1234");
                user.setRole(Role.usr);
                user.setCreatedAt(new Date());
                user.setUpdatedAt(new Date());
                repo.save(user);

                User user2 = new User();
                user2.setUsername("Daisy Wallace");
                user2.setEmail("daisy@email.com");
                user2.setPassword("1234");
                user2.setRole(Role.usr);
                user2.setCreatedAt(new Date());
                user2.setUpdatedAt(new Date());
                repo.save(user2);

                User user3 = new User();
                user3.setUsername("Matt Brown");
                user3.setEmail("matt@email.com");
                user3.setPassword("1234");
                user3.setRole(Role.usr);
                user3.setCreatedAt(new Date());
                user3.setUpdatedAt(new Date());
                repo.save(user3);
            }
        };
    }
}
