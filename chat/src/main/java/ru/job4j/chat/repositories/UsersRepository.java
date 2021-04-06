package ru.job4j.chat.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.models.Person;


public interface UsersRepository extends CrudRepository<Person, Integer> {
    @Query("select u from #{#entityName} u where u.login = ?1")
    Person getUserByLogin(String login);
}
