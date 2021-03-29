package ru.job4j.chat.services;

import org.springframework.stereotype.Service;
import ru.job4j.chat.models.Message;
import ru.job4j.chat.models.Room;
import ru.job4j.chat.models.User;
import ru.job4j.chat.repositories.MessagesRepository;
import ru.job4j.chat.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesService {
    private final MessagesRepository messagesRepository;
    private final RoomService roomService;

    public MessagesService(MessagesRepository messagesRepository, RoomService roomService) {
        this.messagesRepository = messagesRepository;
        this.roomService = roomService;
    }

    public Message sendMessage(Message message) {
        return this.messagesRepository.save(message);
    }

    public List<Message> getMessageListByRoomId(Integer room_id) {
        List<Message> result = new ArrayList<>();
        Room room = this.roomService.getRoomById(room_id);
        if (room != null) {
            result = this.messagesRepository.getMessagesForRoom(room);
        }
        return result;
    }


}
