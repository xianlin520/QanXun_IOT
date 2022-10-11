package vip.xianlin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.xianlin.controller.util.Code;
import vip.xianlin.controller.util.Result;
import vip.xianlin.service.util.EmailService;

@RestController // 标记为控制类
@CrossOrigin // 解决跨域问题
@RequestMapping("email") // 指定请求路径
public class EmailController {
    @Autowired
    EmailService emailService; // 注入邮件发送服务类
    
    @GetMapping("/{email}")
    public Result sendVerificationCode(@PathVariable String email) {
        boolean flag = emailService.sendEmailVerificationCode(email);
        if (flag) {
            return new Result("邮件发送成功");
        }
        return new Result(Code.BUSINESS_ERR, (Object) "邮件发送失败");
    }
    
    
}
