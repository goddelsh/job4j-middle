package ru.job4j.chat.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.models.Role;
import ru.job4j.chat.models.User;
import ru.job4j.chat.services.UsersService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;


    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(this.usersService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return this.usersService.getRoles();
    }

}
