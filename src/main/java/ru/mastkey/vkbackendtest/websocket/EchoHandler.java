package ru.mastkey.vkbackendtest.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class EchoHandler extends TextWebSocketHandler {

    private WebSocketSession clientSession;

    public EchoHandler(WebSocketSession clientSession) {
        this.clientSession = clientSession;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Connection established with that echo server");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Received message from echo server: " + message.getPayload());
        if (clientSession != null && clientSession.isOpen()) {
            try {
                clientSession.sendMessage(new TextMessage(message.getPayload()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}