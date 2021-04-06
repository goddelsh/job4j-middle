package ru.job4j.chat.services;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.chat.models.Person;
import ru.job4j.chat.models.Role;
import ru.job4j.chat.repositories.RolesRepository;
import ru.job4j.chat.repositories.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final BCryptPasswordEncoder encoder;

    public UsersService(UsersRepository usersRepository, RolesRepository rolesRepository, BCryptPasswordEncoder encoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.encoder = encoder;
    }

    public Person createUser(Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        var roles = person.getRoles();
        person.setRoles(roles.stream()
                .map(r -> getRoleByName(r.getName()))
                .filter(r -> r != null)
                .collect(Collectors.toSet()));
        return this.usersRepository.save(person);
    }

    public Person getUserByLogin(String login) {
        var s = this.usersRepository.getUserByLogin(login);
        return s;
    }

    public List<Role> getRoles() {
        return StreamSupport.stream(
                this.rolesRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    public Role getRoleByName(String name) {
        return this.rolesRepository.findByName(name);
    }


}
