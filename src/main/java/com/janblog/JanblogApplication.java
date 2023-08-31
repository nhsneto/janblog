package com.janblog;

import com.janblog.model.Article;
import com.janblog.model.Role;
import com.janblog.model.User;
import com.janblog.repository.ArticleRepository;
import com.janblog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public UserRepository userRepo;

    @Autowired
    public ArticleRepository articleRepo;

    private static final Logger LOG = LoggerFactory.getLogger(JanblogApplication.class);

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (userRepo.findAll().isEmpty() && articleRepo.findAll().isEmpty()) {
                LOG.info("Inserting user John Doe on MongoDB...");
                User user = new User();
                user.setUsername("John Doe");
                user.setEmail("john@email.com");
                user.setPassword("1234");
                user.setRole(Role.usr);
                user.setCreatedAt(new Date());
                user.setUpdatedAt(new Date());
                userRepo.save(user);

                LOG.info("Inserting Article by John Doe on MongoDB...");
                Article article1 = new Article();
                article1.setTitle("John Doe Article");
                article1.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit. John Doe.");
                article1.setUserId(user.getId());
                articleRepo.save(article1);

                LOG.info("Inserting user Daisy Wallace on MongoDB...");
                User user2 = new User();
                user2.setUsername("Daisy Wallace");
                user2.setEmail("daisy@email.com");
                user2.setPassword("1234");
                user2.setRole(Role.usr);
                user2.setCreatedAt(new Date());
                user2.setUpdatedAt(new Date());
                userRepo.save(user2);

                LOG.info("Inserting Article by Daisy Wallace on MongoDB...");
                Article article2 = new Article();
                article2.setTitle("Daisy Wallace Article");
                article2.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Daisy Wallace.");
                article2.setUserId(user2.getId());
                articleRepo.save(article2);

                LOG.info("Inserting user Matt Brown on MongoDB...");
                User user3 = new User();
                user3.setUsername("Matt Brown");
                user3.setEmail("matt@email.com");
                user3.setPassword("1234");
                user3.setRole(Role.usr);
                user3.setCreatedAt(new Date());
                user3.setUpdatedAt(new Date());
                userRepo.save(user3);

                LOG.info("Inserting Article by Matt Brown on MongoDB...");
                Article article3 = new Article();
                article3.setTitle("Daisy Wallace Article");
                article3.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Matt Brown.");
                article3.setUserId(user3.getId());
                articleRepo.save(article3);
            }
        };
    }
}
