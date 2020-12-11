package com.glyxybxhtxt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.glyxybxhtxt.dao")
@EnableAsync  //开启异步注解功能
@EnableScheduling //开启基于注解的定时任务
public class GlyxybxhtxtApplication {
    public static void main(String[] args) {
        SpringApplication.run(GlyxybxhtxtApplication.class, args);
    }
}
