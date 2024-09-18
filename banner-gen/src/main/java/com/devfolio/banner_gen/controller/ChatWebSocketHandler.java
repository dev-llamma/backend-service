package com.devfolio.banner_gen.controller;
import com.devfolio.banner_gen.service.GeminiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    GeminiService geminiService;

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("Session established: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("Session closed: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String usermessage = message.getPayload();
        String response = this.geminiService.getGeminiResponse(usermessage);
        for (WebSocketSession webSocketSession : sessions.values()) {
            if (webSocketSession.isOpen()) {
                TextMessage responseMessage = new TextMessage(response);
                log.info("message: "+message);
                webSocketSession.sendMessage(responseMessage);
            }
        }
    }
}

