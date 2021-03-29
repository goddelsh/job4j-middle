package ru.job4j.chat.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.models.Message;
import ru.job4j.chat.models.Room;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Message, Integer> {
    @Query("select m from #{#entityName} m where m.room = ?1")
    List<Message> getMessagesForRoom(Room room);
}
