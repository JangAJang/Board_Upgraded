package com.board.chat.dto;

public enum MessageType {

    ENTER("님이 입장했습니다."), TALK("");

    private final String text;

    private MessageType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
