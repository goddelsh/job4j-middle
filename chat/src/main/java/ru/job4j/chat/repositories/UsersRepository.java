package ru.job4j.chat.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.models.User;

public interface UsersRepository extends CrudRepository<User, Integer> {
}
