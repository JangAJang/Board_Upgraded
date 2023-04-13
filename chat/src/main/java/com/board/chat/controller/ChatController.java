package com.board.chat.controller;

import com.board.chat.dto.ChatRoom;
import com.board.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestBody String name){
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom(){
        return chatService.findAllRoom();
    }
}
