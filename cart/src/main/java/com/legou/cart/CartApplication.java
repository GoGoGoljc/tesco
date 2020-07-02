package com.legou.cart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author SJXY
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.legou.cart.client")
@MapperScan(basePackages = {"com.legou.cart.mapper"})
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }

}
