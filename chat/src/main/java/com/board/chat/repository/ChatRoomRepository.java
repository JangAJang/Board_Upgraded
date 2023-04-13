package com.board.chat.repository;

import com.board.chat.dto.ChatRoom;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//실제 리포지토리는 아니지만, 일단 HashMap으로 만든 후에 나중에 DB 연동 예정
@Component
public class ChatRoomRepository {
    private final Map<String, ChatRoom> chatRooms = new LinkedHashMap<>();

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public void addRoom(String id, ChatRoom chatRoom){
        chatRooms.put(id, chatRoom);
    }
}
