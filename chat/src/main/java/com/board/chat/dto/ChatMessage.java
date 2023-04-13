package com.board.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.board.chat.dto.MessageType.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;

    public boolean isEntering() {
        return messageType.equals(ENTER);
    }

    public void createEnteringMessage(){
        message = sender + ENTER.getText();
    }
}
