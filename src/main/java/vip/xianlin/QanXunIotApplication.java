package vip.xianlin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class QanXunIotApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(QanXunIotApplication.class, args);
        log.info("===========================");
        log.info("SpringBoot项目启动成功");
        log.info("===========================");
    }
    
}
