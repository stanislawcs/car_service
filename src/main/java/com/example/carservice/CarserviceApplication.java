package com.example.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableCaching
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class CarserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarserviceApplication.class, args);
    }

}
