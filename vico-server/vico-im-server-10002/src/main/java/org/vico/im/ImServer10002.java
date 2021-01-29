package org.vico.im;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ImServer10002 {
    public static void main(String[] args) {
        SpringApplication.run(ImServer10002.class, args);
    }
}