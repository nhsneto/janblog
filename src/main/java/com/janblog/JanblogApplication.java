package com.janblog;

import com.janblog.model.Article;
import com.janblog.model.Comment;
import com.janblog.model.Role;
import com.janblog.model.User;
import com.janblog.repository.ArticleRepository;
import com.janblog.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

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
                LOG.info("Inserting user John Doe into MongoDB...");
                User user1 = new User();
                user1.setUsername("John Doe");
                user1.setEmail("john@email.com");
                user1.setPassword("1234");
                user1.setRole(Role.usr);
                user1.setCreatedAt(Instant.now());
                user1.setUpdatedAt(Instant.now());
                userRepo.save(user1);

                LOG.info("Inserting Article by John Doe into MongoDB...");
                Article article1 = new Article();
                article1.setTitle("John Doe Article");
                article1.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit. John Doe.");
                article1.setUserId(user1.getId());
                articleRepo.save(article1);

                LOG.info("Inserting Comment by John Doe into MongoDB...");
                Comment comment1 = new Comment();
                comment1.setId(ObjectId.get().toString());
                comment1.setUserId(user1.getId());
                comment1.setText("This is John Doe's comment.");
                comment1.setDate(Instant.now());

                LOG.info("Inserting user Daisy Wallace into MongoDB...");
                User user2 = new User();
                user2.setUsername("Daisy Wallace");
                user2.setEmail("daisy@email.com");
                user2.setPassword("1234");
                user2.setRole(Role.usr);
                user2.setCreatedAt(Instant.now());
                user2.setUpdatedAt(Instant.now());
                userRepo.save(user2);

                LOG.info("Inserting Article by Daisy Wallace into MongoDB...");
                Article article2 = new Article();
                article2.setTitle("Daisy Wallace Article");
                article2.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Daisy Wallace.");
                article2.setUserId(user2.getId());
                articleRepo.save(article2);

                LOG.info("Inserting Comment by Daisy Wallace into MongoDB...");
                Comment comment2 = new Comment();
                comment2.setId(ObjectId.get().toString());
                comment2.setUserId(user2.getId());
                comment2.setText("This is Daisy Wallace's comment.");
                comment2.setDate(Instant.now());

                LOG.info("Inserting user Matt Brown into MongoDB...");
                User user3 = new User();
                user3.setUsername("Matt Brown");
                user3.setEmail("matt@email.com");
                user3.setPassword("1234");
                user3.setRole(Role.usr);
                user3.setCreatedAt(Instant.now());
                user3.setUpdatedAt(Instant.now());
                userRepo.save(user3);

                LOG.info("Inserting Article by Matt Brown into MongoDB...");
                Article article3 = new Article();
                article3.setTitle("Daisy Wallace Article");
                article3.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Matt Brown.");
                article3.setUserId(user3.getId());
                articleRepo.save(article3);

                LOG.info("Inserting Comment by Matt Brown into MongoDB...");
                Comment comment3 = new Comment();
                comment3.setId(ObjectId.get().toString());
                comment3.setUserId(user3.getId());
                comment3.setText("This is Matt Brown's comment.");
                comment3.setDate(Instant.now());

                LOG.info("Adding Comments to Article1...");
                List<Comment> comments = Arrays.asList(comment1, comment2, comment3);
                article1.setComments(comments);
                articleRepo.save(article1);
            }
        };
    }
}
