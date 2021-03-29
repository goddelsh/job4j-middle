package ru.job4j.chat.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.models.Role;

public interface RolesRepository extends CrudRepository<Role, Integer> {
}
