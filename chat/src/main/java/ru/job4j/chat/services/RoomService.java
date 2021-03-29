package ru.job4j.chat.services;

import org.springframework.stereotype.Service;
import ru.job4j.chat.models.Role;
import ru.job4j.chat.models.Room;
import ru.job4j.chat.repositories.RoomsRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoomService {

    private final RoomsRepository roomsRepository;

    public RoomService(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    public List<Room> getRooms() {
        return StreamSupport.stream(
                this.roomsRepository.findAll().spliterator(), false
        ).filter(el -> el.getStatus() == 1).collect(Collectors.toList());
    }


    public Room getRoomById(Integer room_id) {
        return this.roomsRepository.findById(room_id).orElse(null);
    }

    public Room createRoom(Room room) {
        return roomsRepository.save(room);
    }

    public boolean deleteRoom(Integer roomId) {
        var result = false;
        Room room = this.roomsRepository.findById(roomId).orElse(null);
        if (room != null) {
            room.setStatus(0);
            this.roomsRepository.save(room);
            result = true;
        }
        return result;
    }
}
