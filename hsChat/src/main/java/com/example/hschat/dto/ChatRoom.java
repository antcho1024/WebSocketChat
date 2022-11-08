package com.example.hschat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class ChatRoom {
    private String roomId;
    private String name;

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}

// pub/sub 방식을 이용하면 구독자가 알아서 관리 되기 때문에
// 아래 처럼 웹소켓 세션관리가 필요 없어짐
// 발송 구현도 알아서 해결되므로 클라에게 메세지 발송하는 구현 필요 없어짐

//@Getter
//public class ChatRoom {
//    private String roomId;
//    private String name;
//    private Set<WebSocketSession> sessions = new HashSet<>(); // 입장한 클라이언트들의 세션 정보
//
//    @Builder
//    public ChatRoom(String roomId, String name) {
//        this.roomId = roomId;
//        this.name = name;
//    }
//
//    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
//        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
//            sessions.add(session); // 입장하면 세션s에 추가
//            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
//        }
//        sendMessage(chatMessage, chatService); // 입장한거면 위에 거치고 메세지 보내기
//    }
//
//    // 채팅룸 모든 세션에게 메세지 보냄 for문 돌면서 하나씩 보내
//    public <T> void sendMessage(T message, ChatService chatService) {
//        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
//    }
//}