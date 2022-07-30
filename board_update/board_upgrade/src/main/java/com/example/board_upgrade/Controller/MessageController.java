package com.example.board_upgrade.Controller;

import com.example.board_upgrade.Dto.MessageDto;
import com.example.board_upgrade.Entity.User;
import com.example.board_upgrade.Repository.UserRepository;
import com.example.board_upgrade.Response.Response;
import com.example.board_upgrade.Service.MessageService;
import com.example.board_upgrade.auth.PrincipalDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MessageController {
    private final MessageService messageService;
    private final UserRepository userRepository;

    @ApiOperation(value = "쪽지 보내기", notes = "쪽지 보내기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/messages/write")
    public Response<?> sendMessage(@RequestBody MessageDto messageDto, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        User user = principalDetails.getUser();
        messageDto.setSenderName(user.getName());
        return new Response<>("성공", "쪽지를 보냈습니다. ", messageService.write(messageDto));
    }

    @ApiOperation(value = "받은 편지함 읽기", notes = "받은 편지함 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/received")
    public Response<?> getReceivedMessage(Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        User user = principalDetails.getUser();
        return new Response<>("성공", "받은 쪽지를 불러왔습니다. ", messageService.receivedMessage(user));
    }

    @ApiOperation(value = "받은 쪽지 삭제", notes = "해당 받은 쪽지를 삭제합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/received/{id}/delete")
    public Response<?> deleteReceivedMessage(@PathVariable("id") Integer id, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        User user = principalDetails.getUser();
        MessageDto messageDto = messageService.findMessageById(id);
        if(messageDto.getReceiverName().equals(user.getName())){
            return new Response<>("성공", "쪽지를 삭제했습니다. ", messageService.deleteMessageByReceiver(id, user));
        }
        else return new Response<>("실패", "사용자 정보가 다릅니다.", null);
    }

    @ApiOperation(value = "받은 편지함 읽기", notes = "받은 편지함 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/sent")
    public Response<?> getSentMessage( Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        User user = principalDetails.getUser();
        return new Response<>("성공", "보낸 쪽지를 불러왔습니다. ", messageService.sentMessage(user));
    }

    @ApiOperation(value = "받은 쪽지 삭제", notes = "해당 받은 쪽지를 삭제합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/sent/{id}/delete")
    public Response<?> deleteSentMessage(@PathVariable("id") Integer id, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        User user = principalDetails.getUser();
        MessageDto messageDto = messageService.findMessageById(id);
        if(messageDto.getSenderName().equals(user.getName())){
            return new Response<>("성공", "쪽지를 삭제했습니다. ", messageService.deleteMessageBySender(id, user));
        }
        else return new Response<>("실패", "사용자 정보가 다릅니다.", null);
    }


}
