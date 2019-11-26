package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketChatServer.class);

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg) {
        onlineSessions.forEach((s, session) -> {
            if(session.isOpen()){
                try {
                    session.getBasicRemote().sendText(msg);
                } catch (IOException e) {
                    logger.error("Error sending message: " + e.getLocalizedMessage());
                }
            }
        });

    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session)  {
       logger.info("Opening new session for: " + session.getId());
       onlineSessions.put(session.getId(),session);
       Message message = new Message(session.getId(),"", "LOGIN", onlineSessions.size());
       sendMessageToAll(JSON.toJSONString(message));

    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        logger.info("Processing Messsge from: " + session.getId());
        Message message = JSON.parseObject(jsonStr, Message.class);
        logger.info("Parsed Message: " + message.toString());
        message.setType("SPEAK");
        message.setOnlineCount(onlineSessions.size());
        sendMessageToAll(JSON.toJSONString(message));
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        logger.info("Closing session for: " + session.getId());
        onlineSessions.remove(session.getId(),session);
        //Message message = JSON.parseObject(jsonStr, Message.class);
        Message message = new Message(session.getId(),"", "LOGOUT", onlineSessions.size());
        sendMessageToAll(JSON.toJSONString(message));
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
