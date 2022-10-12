package vip.xianlin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import vip.xianlin.util.JwtUtil;

@SpringBootTest
public class TestJwtUtil {
    @Test
    void test() {
        boolean b = JwtUtil.checkSignByToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyYjU0NzE1MS00ODhmLTRhZmUtOTc3NC04MDhiMWE5ZTU3MWIiLCJleHAiOjE2NjgxMzI2NzAsImluZm8iOnsiaWQiOjEsImFjY291bnQiOiJhZG1pbkBxcS5jb20ifX0.xVXZrRkXj6fghOmddn6B8CTP4F8I4lmslWbeP_Pnk1U");
        System.out.println(b);
    
    }
}
