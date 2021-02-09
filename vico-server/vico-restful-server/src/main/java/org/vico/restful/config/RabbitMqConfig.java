package org.vico.restful.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class RabbitMqConfig {

    @Resource
    private Environment environment;

    // 声明交换机
    @Bean(name = "direct_exchange")
    public Exchange directExchange(){
        log.info("绑定交换机: " + environment.getProperty("auth.exchange"));
        return new DirectExchange(environment.getProperty("auth.exchange"));
    }

    // 声明队列
    @Bean(name = "message_queue")
    public Queue messageQueue(){
        log.info("绑定队列: " + environment.getProperty("auth.queue"));
        return new Queue(environment.getProperty("auth.queue"), true);
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
