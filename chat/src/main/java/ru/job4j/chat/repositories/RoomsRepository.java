package ru.job4j.chat.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.models.Room;

public interface RoomsRepository extends CrudRepository<Room, Integer> {
}
