package ru.job4j.chat.services;


import org.springframework.stereotype.Service;
import ru.job4j.chat.models.Role;
import ru.job4j.chat.models.User;
import ru.job4j.chat.repositories.RolesRepository;
import ru.job4j.chat.repositories.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    public UsersService(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    public User createUser(User user) {
        return this.usersRepository.save(user);
    }

    public List<Role> getRoles() {
        return StreamSupport.stream(
                this.rolesRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }


}
