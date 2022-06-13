package zero.springboot.study.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebsocketConfiguration {

    /**
     * 这个配置类的作用是要注入ServerEndpointExporter，会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     * 如果采用tomcat容器进行部署启动，而不是直接使用springboot的内置容器
     * 就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理。
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}