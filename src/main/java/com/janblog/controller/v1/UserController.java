package com.janblog.controller.v1;

import com.janblog.model.User;
import com.janblog.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user) {
        return ResponseEntity.ok(userRepo.save(user));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody User user) {
        Optional<User> usr = userRepo.findById(user.getId());

        if (usr.isEmpty()) {
            return ResponseEntity.ok("the user doesn't exist");
        }
        return ResponseEntity.ok(userRepo.save(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        userRepo.deleteById(id);
        return ResponseEntity.ok().body("deleted");
    }
}
