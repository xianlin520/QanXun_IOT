package vip.xianlin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vip.xianlin.service.EmailService;

@SpringBootTest
class QanXunIotApplicationTests {
    @Autowired
    private EmailService emailService;
    
    @Test
    void contextLoads() {
        emailService.sendEmailVerificationCode("2683971783@qq.com");
    }
}
