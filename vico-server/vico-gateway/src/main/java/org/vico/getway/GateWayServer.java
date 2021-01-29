package org.vico.getway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GateWayServer {
    public static void main(String[] args) {
        SpringApplication.run(GateWayServer.class, args);
    }
}
