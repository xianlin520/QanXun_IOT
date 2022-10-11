package vip.xianlin.controller.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vip.xianlin.exception.BusinessException;

@RestControllerAdvice // 异常处理器
@Slf4j // 启用日志
public class ProjectExceptionAdvice {
    
    // 捕获业务异常
    @ExceptionHandler(value = BusinessException.class)
    public Result exceptionHandler(BusinessException e) {
        log.error(String.valueOf(e));
        return new Result(Code.BUSINESS_ERR, e.getMessage(), "业务异常, 请重试"); // 返回异常结果
    }
    
    // 捕获未知异常
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error(String.valueOf(e));
        return new Result(Code.SYSTEM_UNKEOW_ERR, null, "服务器内部错误"); // 返回异常结果
    }
}
