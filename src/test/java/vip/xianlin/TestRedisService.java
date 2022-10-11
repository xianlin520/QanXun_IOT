package vip.xianlin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vip.xianlin.service.util.RedisService;

@SpringBootTest
public class TestRedisService {
    @Autowired
    RedisService redisService;
    
    @Test
    void contextLoads() {
        System.out.println(redisService.getData("11"));
    }
}
