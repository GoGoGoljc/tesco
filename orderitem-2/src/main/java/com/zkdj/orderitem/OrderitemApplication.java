package com.zkdj.orderitem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages="com.zkdj.orderitem.client")
public class OrderitemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderitemApplication.class, args);
    }

}
