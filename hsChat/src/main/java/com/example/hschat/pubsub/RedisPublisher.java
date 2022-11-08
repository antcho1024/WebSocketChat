package com.example.hschat.pubsub;

import com.example.hschat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service

// 메세지 발행
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
    //redis가 메세지를 처리하고 구독자들한테 메세지 보내줌
    // 이일을 담당하던 곳 : handler -> controller -> (RedisPublisher,) RedisSubscriber
}