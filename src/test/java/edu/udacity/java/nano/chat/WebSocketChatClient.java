package edu.udacity.java.nano.chat;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
public class WebSocketChatClient {

    CountDownLatch countDownLatch = new CountDownLatch(1);
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to server");
        this.session = session;
        countDownLatch.countDown();
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Closing a WebSocket");
    }

    public CountDownLatch getLatch() {
        return countDownLatch;
    }

    public void sendMessage(String str) {
        try {
            session.getBasicRemote().sendText(str);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
