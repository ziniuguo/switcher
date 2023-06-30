package io.catroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class TestThread extends Thread {
    @Autowired
    MessageChannel helloChannel;
    @Override
    public void run() {
        helloChannel.send(new GenericMessage<>("this is hello world payload"));
    }

}
