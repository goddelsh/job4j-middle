package ru.job4j.chat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.models.Message;
import ru.job4j.chat.services.MessagesService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    private final MessagesService messagesService;

    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @GetMapping("/{id}")
    public List<Message> getMessages(@PathVariable Integer room_id) {
        return this.messagesService.getMessageListByRoomId(room_id);
    }

    @PostMapping("/")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        var result = this.messagesService.sendMessage(message);
        return result != null ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(new Message(), HttpStatus.BAD_REQUEST);
    }

}
