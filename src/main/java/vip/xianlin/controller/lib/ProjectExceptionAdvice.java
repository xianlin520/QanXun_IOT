package vip.xianlin.controller.lib;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.xianlin.exception.BusinessException;

@RestControllerAdvice // 异常处理器
public class ProjectExceptionAdvice {
    
    // 捕获业务异常
    @ExceptionHandler(value = BusinessException.class)
    public Result exceptionHandler(BusinessException e) {
        
        return new Result(Code.BUSINESS_ERR, e.getMessage(), "业务异常, 请重试"); // 返回异常结果
    }
    
    // 捕获未知异常
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        return new Result(Code.SYSTEM_UNKEOW_ERR, null, "服务器内部错误"); // 返回异常结果
    }
}
