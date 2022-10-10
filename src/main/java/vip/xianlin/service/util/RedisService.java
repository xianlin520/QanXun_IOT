package vip.xianlin.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    // 提供键值对, 往数据库内添加数据
    public boolean putData(String key, String value){
        redisTemplate.opsForValue().set(key, value, 5, TimeUnit.MINUTES); // 添加到Redis数据库, 五分钟后过期
        return Boolean.TRUE.equals(redisTemplate.hasKey(key)); // 查询数据内是否有数据, 并返回Bool值
    }
}
