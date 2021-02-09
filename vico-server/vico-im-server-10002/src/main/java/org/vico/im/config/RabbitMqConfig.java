package org.vico.im.config;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.vico.im.proto.ProtoMessage;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class RabbitMqConfig {

    @Resource
    private Environment environment;

    // 序列化规则
    @Bean
    public MessageConverter messageConverter(){
        return new AbstractMessageConverter() {
            @SneakyThrows
            @Override
            protected Message createMessage(Object o, MessageProperties messageProperties) {
                byte[] data = ((ProtoMessage.TextMessageRequest) o).toByteArray();
                System.out.println(ProtoMessage.TextMessageRequest.parseFrom(data).getContent());
                return new Message(data, messageProperties);
            }

            @Override
            public Object fromMessage(Message message) throws MessageConversionException {
                try{
                    return ProtoMessage.TextMessageRequest.parseFrom(message.getBody());
                }catch (InvalidProtocolBufferException e){
                    log.warn("消息格式错误，无法解析");
                    return null;
                }
            }
        };
    }

    // 声明交换机
    @Bean(name = "direct_exchange")
    public DirectExchange directExchange(){
        log.info("绑定交换机: " + environment.getProperty("auth.exchange"));
        return new DirectExchange(environment.getProperty("auth.exchange"));
    }

    // 声明队列
    @Bean(name = "message_queue")
    public Queue messageQueue(){
        log.info("绑定队列: " + environment.getProperty("auth.queue"));
        return new Queue("127.0.0.1#10002", true);
    }

    // 绑定交换机和队列
    @Bean(name = "queue_exchange_binding")
    public Binding queueExchangeBinding(@Qualifier("direct_exchange") Exchange exchange,
                                        @Qualifier("message_queue") Queue queue){
        log.info("绑定交换机" + exchange.getName() + "和队列" + queue.getName());
            return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(environment.getProperty("auth.queue"))
                .noargs();
    }
}
