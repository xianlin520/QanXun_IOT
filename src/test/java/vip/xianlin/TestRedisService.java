package vip.xianlin;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vip.xianlin.service.util.RedisService;

@Slf4j
@SpringBootTest
public class TestRedisService {
    @Autowired
    RedisService redisService;
    @Test
    void contextLoads() {
        log.info("测试redis数据库增删查");
        // 测试数据库添加
        redisService.putData5min("test", "testRedis");
        // 测试数据库读取
        redisService.getData("test");
        // 测试数据库删除
        redisService.delData("test");
        // 测试数据库是否删除成功
        boolean test = redisService.getData("test") == null;
        log.info("测试结果"+(test?"正常":"异常"));
    }
}
