package ru.job4j.chat.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.models.Room;
import ru.job4j.chat.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomsController {

    private final RoomService roomService;

    public RoomsController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/")
    public List<Room> getAllRooms() {
        return this.roomService.getRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getAllRooms(@PathVariable Integer room_id) {
        Room room = this.roomService.getRoomById(room_id);
        return room != null ? new ResponseEntity<>(room, HttpStatus.OK)
                : new ResponseEntity<>(new Room(), HttpStatus.NOT_FOUND);
    }


    @PostMapping("/create")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        return new ResponseEntity<>(this.roomService.createRoom(room), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> createRoom(@PathVariable Integer room_id) {
        return this.roomService.deleteRoom(room_id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED) ;
    }

}
