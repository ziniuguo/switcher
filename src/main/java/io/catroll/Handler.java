package io.catroll;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class Handler {
    public void handleMessage(Message<Object> message) {
        System.out.println("here is the message: " + message.getPayload());
    }
}
