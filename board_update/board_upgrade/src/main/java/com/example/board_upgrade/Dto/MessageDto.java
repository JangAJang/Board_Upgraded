package com.example.board_upgrade.Dto;

import com.example.board_upgrade.Entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    @NotNull
    private int id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String senderName;
    @NotNull
    private String receiverName;

    public static MessageDto toDto(Message message){
        return new MessageDto(
                message.getId(),
                message.getTitle(),
                message.getContent(),
                message.getSender().getName(),
                message.getReceiver().getName()
        );
    }

}
