package zero.springboot.study.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint("/webSocket/{userId}")
@Component
@Slf4j
public class MyWebsocketServer {
    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        log.info("有新的客户端连接了: session id = {}, userId = {}", session.getId(), userId);
        //将新用户存入在线的组
        clients.put(userId, session);
    }

    /**
     * 客户端关闭
     *
     * @param session session
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId, Session session) {
        log.info("有用户断开了, id为:{}", userId);
        //将掉线的用户移除在线的组里
        clients.remove(userId);
    }

    /**
     * 发生错误
     *
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     *
     * @param message 消息对象
     */
    @OnMessage
    public void onMessage(String message, @PathParam("userId") String userId) {
        log.info("服务端收到客户端发来的消息: {}", message);
        this.sendMessageToClient(userId, message);
    }

    /**
     * 发送消息给指定客户端
     * @param sessionId
     * @param message
     * @return
     */
    public boolean sendMessageToClient(String userId, String message) {
        Session session = clients.get(userId);
        if (Objects.isNull(session)) {
            log.info("session is null, userId = {}", userId);
            return false;
        }
        if (!session.isOpen()) {
            log.info("session is closed, userId = {}", userId);
            return false;
        }
        log.info("start send message");
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("send message error", e);
        }
        return false;
    }

    /**
     * 群发消息
     *
     * @param message 消息内容
     */
    private void sendAll(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            sessionEntry.getValue().getAsyncRemote().sendText(message);
        }
    }
}
