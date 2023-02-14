package vip.xianlin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.xianlin.controller.util.Code;
import vip.xianlin.controller.util.Result;
import vip.xianlin.domain.DevicesData;
import vip.xianlin.service.DevicesService;
import vip.xianlin.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@RestController // 标记为控制类
@CrossOrigin // 解决跨域问题
@RequestMapping("devices") // 指定请求路径
@Slf4j
public class DevicesController {
    @Autowired
    DevicesService devicesService; // 注入设备信息服务类
    
    // 插入设备信息
    @PutMapping
    public Result insertDevicesData(@RequestBody DevicesData devicesData, HttpServletRequest res) {
        Integer userId = JwtUtil.getIdFormRes(res);
        Boolean aBoolean = devicesService.insertDevicesData(devicesData, userId);
        return new Result(aBoolean ? Code.OK : Code.BUSINESS_ERR, "操作成功");
    }
    
    // 根据用户ID获取设备信息
    @GetMapping
    public Result getDevicesDataForUser(HttpServletRequest res) {
        Integer userId = JwtUtil.getIdFormRes(res);
        return new Result(Code.OK, devicesService.getDevicesDataForUser(userId));
    }
    
    
}
