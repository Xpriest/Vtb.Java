package ru.vtb.java.lesson11.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vtb.java.lesson11.models.User;
import ru.vtb.java.lesson11.services.UserService;
import ru.vtb.java.lesson11.utils.AppError;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> findAllItems() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        Optional<User> result = userService.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "User with id: " + id + " not found"), HttpStatus.NOT_FOUND);
    }
}
