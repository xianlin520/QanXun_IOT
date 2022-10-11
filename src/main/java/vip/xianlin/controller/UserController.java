package vip.xianlin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.xianlin.controller.util.Code;
import vip.xianlin.controller.util.Result;
import vip.xianlin.domain.UserData;
import vip.xianlin.service.util.RedisService;
import vip.xianlin.util.JwtUtil;
import vip.xianlin.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController // 标记为控制类
@CrossOrigin // 解决跨域问题
@RequestMapping("users") // 指定请求路径
@Slf4j // 启用日志
public class UserController {
    @Autowired
    private UserService userService; //注入服务类
    @Autowired
    private RedisService redisService;
    // 使用Get查询账号是否存在, 存在=>true, 不存在=>false;
    @GetMapping("/{account}")
    public Result queryAccount(@PathVariable String account) {
        Boolean flag = userService.queryAccount(account);
        return new Result(flag, flag ? "账号存在" : "账号不存在");
    }
    
    // 使用POST查询账号密码是否正确, 正确就返回Token和基本信息
    @PostMapping
    public Result queryAccountAndPassword(@RequestBody UserData userData) {
        UserData retUserData = userService.queryAccountAndPassword(userData);   // 传入UserData对象 进行数据查询
        // 如果retUserData不为空, 则表示查询成功
        if (retUserData != null) {
            String uuid = UUID.randomUUID().toString(); // 生成不重复UUID
            Map<String, Object> info = new HashMap<>();
            info.put("account", retUserData.getAccount());  // 存入账号信息
            info.put("name", retUserData.getName());    // 存入昵称信息
            String token = JwtUtil.sign(uuid, info);    // 存入UUID和info, 生成JWT加密Token
            return new Result(token, "账号密码正确, 返回token");    // 账号密码正确, 返回token
        }
        return new Result((Object) null, "账号或密码错误");
    }
    
    // 使用Put添加用户数据, 接收前端指定数据
    @PutMapping
    public Result appUserData(@RequestBody Map<String,Object> mapData) {
        String account = (String) mapData.get("account"); // 提取账号信息
        String check = (String) mapData.get("check"); // 提取验证码信息
        // 判断账号信息和验证码信息是否吻合, 否则直接返回
        if (!Objects.equals(redisService.getData(account), check)) {
            return new Result("邮箱验证码不正确");
        }
        String name = (String) mapData.get("name");
        String password = (String) mapData.get("password");
        UserData userData = new UserData(name, account, password);
        try {
            userService.addUserData(userData); // 调用用户服务类, 插入数据到数据库
            return new Result("数据添加成功");
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return new Result(Code.SQLERR, (Object) "数据添加失败");
        }
    }
    
    @PutMapping("/updata")
    public Result upUserData() {
        return new Result("更新数据");
    }
    
}
