package io.catroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class Beans {
    @Autowired Handler handler;
    @Bean public MessageChannel helloChannel() { return new DirectChannel(); }
    @Bean @ServiceActivator(inputChannel = "helloChannel")
    public MessageHandler helloChannelHandler() throws NoSuchMethodException {
        return new ServiceActivatingHandler(handler, handler.getClass().getDeclaredMethod("handleMessage", Message.class));
    }
}
