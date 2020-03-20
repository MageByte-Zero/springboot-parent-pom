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


@ServerEndpoint("/test/{code}")
@Component
@Slf4j
public class MyWebsocketServer {
    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("code") String code) {
        log.info("有新的客户端连接了: {}, code = {}", session.getId(), code);
        //将新用户存入在线的组
        clients.put(session.getId(), session);
    }

    /**
     * 客户端关闭
     *
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        log.info("有用户断开了, id为:{}", session.getId());
        //将掉线的用户移除在线的组里
        clients.remove(session.getId());
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
    public void onMessage(String message) {
        log.info("服务端收到客户端发来的消息: {}", message);
        this.sendAll(message);
    }

    /**
     * 发送消息给指定客户端
     * @param sessionId
     * @param message
     * @return
     */
    public boolean sendMessageToClient(String sessionId, String message) {
        Session session = clients.get(sessionId);
        if (Objects.isNull(session)) {
            log.info("session is null, sessionId = {}", sessionId);
            return false;
        }
        if (!session.isOpen()) {
            log.info("session is closed, sessionId = {}", sessionId);
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
