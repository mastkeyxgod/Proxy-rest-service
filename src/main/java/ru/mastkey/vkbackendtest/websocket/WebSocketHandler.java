package ru.mastkey.vkbackendtest.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final static String ECHO_SERVER_URI = "wss://echo.websocket.org/";

    private WebSocketSession echoSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        WebSocketClient client = new StandardWebSocketClient();
        echoSession = client.doHandshake(new EchoHandler(session), ECHO_SERVER_URI).get();
        System.out.println("Connection established with this echo server");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (echoSession != null && echoSession.isOpen()) {
            echoSession.sendMessage(new TextMessage(message.getPayload()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Connection closed with echo server");
    }
}