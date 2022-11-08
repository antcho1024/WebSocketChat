package com.example.hschat.controller;

import com.example.hschat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")
    // "pub/chat/message"를 통해 메세지 보내면 /sub/chat/room/{} 을 구독하고 있는 사람들한테 메세지 보내줌
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) // 입장한거면 이 과정 거쳐서 메세지 만들고
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
    // 이전에 Handler 에서 하던일 여기서 함
}
