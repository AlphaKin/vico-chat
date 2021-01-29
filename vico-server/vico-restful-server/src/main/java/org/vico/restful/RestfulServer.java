package org.vico.restful;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(value = "org.vico.restful.mapper")
public class RestfulServer {
    public static void main(String[] args) {
        SpringApplication.run(RestfulServer.class, args);
    }
}
