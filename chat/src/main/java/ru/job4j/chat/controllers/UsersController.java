package ru.job4j.chat.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.models.Person;
import ru.job4j.chat.models.Role;
import ru.job4j.chat.services.UsersService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;


    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


    @GetMapping("/get/{name}")
    public ResponseEntity<Person> getByName(@PathVariable String name) {
        return new ResponseEntity<>(this.usersService.getUserByLogin(name), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Person> createUser(@RequestBody Person person) {
        return new ResponseEntity<>(this.usersService.createUser(person), HttpStatus.CREATED);
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        usersService.createUser(person);
    }


    @GetMapping("/roles")
    public List<Role> getRoles() {
        return this.usersService.getRoles();
    }

}
