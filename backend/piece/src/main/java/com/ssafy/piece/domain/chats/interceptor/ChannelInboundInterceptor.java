package com.ssafy.piece.domain.chats.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class ChannelInboundInterceptor implements ChannelInterceptor {

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);

        System.out.println("command:" + header.getCommand());

        switch (header.getCommand()) {
            case SUBSCRIBE:
                System.out.println("채팅방 입장이 감지되었어요.");
                break;
            case UNSUBSCRIBE:
                System.out.println("채팅방 퇴장이 감지되었어요.");
        }

        System.out.println("destination:" + header.getDestination());
        System.out.println("userNumber header:" + header.getFirstNativeHeader("userNumber"));

        return message;
    }
}