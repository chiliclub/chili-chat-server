package com.chiliclub.chilichat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹소켓에 접속하기 위한 endpoint 설정 ex) localhost:8080/ws
        // cors 설정 (현재 와읻드카드)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        /**
         * Message Publish
         * [/pub/chat/message]
         * 채팅 메시지 전송 헤더 prefix
         */
        registry.setApplicationDestinationPrefixes("/pub");

        /**
         * Subscribe
         * [/sub/chat-room/{chat_room_no}]
         * 특정 채팅방을 구독(참여) prefix
         */
        registry.enableSimpleBroker("/sub");
    }
}
