package com.janblog.controller.v1;

import com.janblog.dto.UserDTO;
import com.janblog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("janblog/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable String id) {
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody @Valid UserDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(dto));
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable String id, @RequestBody @Valid UserDTO dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
