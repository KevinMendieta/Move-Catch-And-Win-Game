package edu.eci.arsw.game.controllers.mom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @author KevinMendieta
 */
@Configuration
@EnableWebSocketMessageBroker
public class GameWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    
    @Value("${messagesServer.ip}")
    private String messagesIpServer;
    
    @Value("${messagesServer.login}")
    private String messagesServerLogin;
    
    @Value("${messagesServer.passwd}")
    private String messagesServerPasswd;
    
    @Value("${messagesServer.port}")
    private int messagesServerPort;
    
    @Value("${messagesServer.runInHeroku}")
    private boolean runInHeroku;
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        if ( runInHeroku ) {
            config.enableSimpleBroker("/topic");
        } else {
            config.enableStompBrokerRelay("/topic").setRelayHost(messagesIpServer).setRelayPort(messagesServerPort).
                setClientLogin(messagesServerLogin).
                setClientPasscode(messagesServerPasswd).
                setSystemLogin(messagesServerLogin).
                setSystemPasscode(messagesServerPasswd).
                setVirtualHost(messagesServerLogin);
        }
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stompendpoint").setAllowedOrigins("*").withSockJS();
    }

}
