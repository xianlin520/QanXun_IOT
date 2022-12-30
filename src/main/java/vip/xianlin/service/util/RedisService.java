package vip.xianlin.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    // 提供键值, 返回数据库内值
    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);    // 如果有数据则返回值, 如果没有就返回null
    }
    
    // 提供键值对, 往数据库内添加数据, 时效为5分钟
    public boolean putData5min(String key, String value) {
        redisTemplate.opsForValue().set(key, value, 5, TimeUnit.MINUTES); // 添加到Redis数据库, 五分钟后过期
        return Boolean.TRUE.equals(redisTemplate.hasKey(key)); // 查询数据内是否有数据, 并返回Bool值
    }
    
    // 提供键值, 删除数据库内数据
    public boolean delData(String key) {
        redisTemplate.delete(key);
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
