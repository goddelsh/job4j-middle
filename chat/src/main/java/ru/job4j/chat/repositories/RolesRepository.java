package ru.job4j.chat.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.models.Role;

public interface RolesRepository extends CrudRepository<Role, Integer> {
    @Query("select r from #{#entityName} r where r.name = ?1")
    Role findByName(String name);
}
