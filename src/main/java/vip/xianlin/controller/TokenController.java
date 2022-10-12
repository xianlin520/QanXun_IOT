package vip.xianlin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 标记为控制类
@CrossOrigin // 解决跨域问题
@RequestMapping("token") // 指定请求路径
@Slf4j // 启用日志
public class TokenController {
    @GetMapping
    public String token() {
        return "token";
    }
}
