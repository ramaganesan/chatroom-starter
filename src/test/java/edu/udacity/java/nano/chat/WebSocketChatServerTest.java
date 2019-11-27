package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;



public class WebSocketChatServerTest {



    static final String WEBSOCKET_URI = "ws://localhost:8080/chat";
    private static WebSocketChatClient webSocketChatClient;
    private static WebSocketContainer webSocketContainer;

    //@BeforeClass
    public static void setUp() throws Exception{
        webSocketChatClient = new WebSocketChatClient();
        webSocketContainer = ContainerProvider.getWebSocketContainer();
        System.out.println(WEBSOCKET_URI);
        webSocketContainer.connectToServer(webSocketChatClient,new URI(WEBSOCKET_URI));
    }

   // @Test
    public void sendMessage(){
        Message message = new Message("test", "hello", "SPEAK",1);
        String messageString = JSON.toJSONString(message);
        webSocketChatClient.sendMessage(messageString);
    }


}
