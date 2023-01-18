package vip.xianlin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vip.xianlin.util.BeanUtil;

@Slf4j
@MapperScan("vip.xianlin.dao")
@SpringBootApplication
public class QanXunIotApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(QanXunIotApplication.class, args);
        log.info("===========================");
        log.info("SpringBoot项目启动成功");
        log.info("===========================");
    }
    
    // 允许在工具类内调用服务类
    @Bean
    public BeanUtil beanUtil() {
        return new BeanUtil();
    }
}
