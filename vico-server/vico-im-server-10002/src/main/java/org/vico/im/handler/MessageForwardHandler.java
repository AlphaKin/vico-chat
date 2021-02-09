package org.vico.im.handler;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
public class MessageForwardHandler {

//    @RabbitListener(queues = "127.0.0.1#10002")
//    public void charge(String info) {
//        System.out.println("获取MQ消息: " + info);
//    }

}
