package com.example.board_upgrade.Service;

import com.example.board_upgrade.Dto.MessageDto;
import com.example.board_upgrade.Entity.Message;
import com.example.board_upgrade.Entity.User;
import com.example.board_upgrade.Repository.MessageRepository;
import com.example.board_upgrade.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional
    public MessageDto write(MessageDto messageDto){
        User receiver = userRepository.findByName(messageDto.getReceiverName());
        User sender = userRepository.findByName(messageDto.getSenderName());
        Message message = new Message();
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setTitle(messageDto.getTitle());
        message.setContent(messageDto.getContent());
        message.setDeletedBySender(false);
        message.setDeletedByReceiver(false);
        messageRepository.save(message);
        return MessageDto.toDto(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDto> receivedMessage(User user){
        List<Message> messages = messageRepository.findAllByReceiver(user);
        List<MessageDto> messageDtos = new ArrayList<>();
        for(Message message : messages){
            if(!message.isDeletedByReceiver()){
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }

    @Transactional
    public Object deleteMessageByReceiver(int id, User user){
        Message message = messageRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 메시지를 찾을 수 없습니다. ");
        });
        if(user == message.getSender()){
            message.deleteByReceiver();
            if(message.isDeleted()){
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        }
        else{
            return new IllegalArgumentException("유저 정보가 일치하지 않습니다. ");
        }
    }

    @Transactional(readOnly = true)
    public List<MessageDto> sentMessage(User user){
        List<Message> messages = messageRepository.findAllBySender(user);
        List<MessageDto> messageDtos = new ArrayList<>();
        for(Message message : messages){
            if(!message.isDeletedBySender()){
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }

    @Transactional
    public Object deleteMessageBySender(int id, User user){
        Message message = messageRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 메시지를 찾을 수 없습니다. ");
        });
        if(user == message.getSender()){
            message.deleteBySender();
            if(message.isDeleted()){
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        }
        else return new IllegalArgumentException("유저 정보가 일치하지 않습니다. ");
    }

    @Transactional(readOnly = true)
    public MessageDto findMessageById(int id){
        Message message = messageRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 메시지를 찾을 수 없습니다. ");
        });
        return MessageDto.toDto(message);
    }
}
