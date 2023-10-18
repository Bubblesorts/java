package com.bluemsunblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.bluemsunblog.dao")
@SpringBootApplication
public class BluemsunblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BluemsunblogApplication.class, args);
    }


}
