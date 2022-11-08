package com.example.hschat.handler;

import com.example.hschat.dto.ChatMessage;
import com.example.hschat.dto.ChatRoom;
import com.example.hschat.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

//@Slf4j
//@Component
//public class WebSockChatHandler extends TextWebSocketHandler{
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload {}", payload);
//        TextMessage textMessage = new TextMessage("Welcome chatting sever~^^");
//        session.sendMessage(textMessage);
//    }
//}

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        // 웹소켓 클라이언트로부터 채팅 메세지 받아서 채팅 메시지 객체로 변환
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        // 메세지에 담긴 채팅방 ID로 발송대상 채팅방 정보 조회
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        // 채팅방에 있는 모든 클라이언트(WebSocket session)애개 메세지 발송
        room.handleActions(session, chatMessage, chatService); // 여기서 session 은 메세지 발송한 사람 세션 (첫 입장일까봐 보내줌)
    }
    // 정리하면 메세지 오면 그거 처리하고
    // 메세지에서 방 찾아내서 그 방에 연결된 세션들한테 그 메세지 다 보냄
}