package com.janblog.controller.v1;

import com.janblog.dto.EmailDTO;
import com.janblog.dto.PasswordDTO;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable String id, @RequestBody @Valid PasswordDTO dto) {
        userService.changePassword(id, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/change-email")
    public ResponseEntity<?> changeEmail(@PathVariable String id, @RequestBody @Valid EmailDTO dto) {
        userService.changeEmail(id, dto);
        return ResponseEntity.ok().build();
    }
}
