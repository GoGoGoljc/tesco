package com.zpark.sort;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.zpark.sort.client")
@EnableCaching
@MapperScan({"com.zpark.sort.mapper"})
public class SortApplication {

    public static void main(String[] args) {
        SpringApplication.run(SortApplication.class, args);
    }

}
