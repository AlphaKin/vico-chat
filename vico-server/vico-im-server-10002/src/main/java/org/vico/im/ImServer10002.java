package org.vico.im;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(value = "org.vico.im.mapper")
public class ImServer10002 {
    public static void main(String[] args) {
        SpringApplication.run(ImServer10002.class, args);
    }
}