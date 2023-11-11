package com.bluemsunblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.bluemsunblog.dao")
@ComponentScan("com.bluemsunblog.*")
@SpringBootApplication
@EnableScheduling
public class BluemsunblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BluemsunblogApplication.class, args);
    }


}
