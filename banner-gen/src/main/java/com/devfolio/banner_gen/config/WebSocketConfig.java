package com.devfolio.banner_gen.config;
import com.devfolio.banner_gen.controller.ChatWebSocketHandler;
import com.devfolio.banner_gen.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@EnableWebMvc
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private GeminiService geminiService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler(geminiService), "/chat").setAllowedOrigins("*");
    }

    @Bean
    public ChatWebSocketHandler chatWebSocketHandler(GeminiService geminiService) {
        return new ChatWebSocketHandler();
    }
}

