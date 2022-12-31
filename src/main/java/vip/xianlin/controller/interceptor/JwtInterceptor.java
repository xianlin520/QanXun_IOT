package vip.xianlin.controller.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import vip.xianlin.exception.BusinessException;
import vip.xianlin.util.JwtUtil;
import vip.xianlin.controller.util.Code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component // 设置为Spring的组件
@Slf4j // 启用日志
public class JwtInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        //从 http 请求头中取出 token
        String token = request.getHeader("Authorization");
        
        
        // 验证token是否是null
        if (token == null) {
            throw new BusinessException(Code.BUSINESS_ERR ,"无Token, 请在请求头内携带Token");
        }
        // 验证token是否合法, 是否过期
        if (!JwtUtil.checkSign(token)) {
            throw new BusinessException(Code.BUSINESS_ERR ,"Token失效, 请重新获取Token");
        }
        // 验证token内id信息是否存在
        if (!JwtUtil.checkSignByToken(token)) {
            throw new BusinessException(Code.BUSINESS_ERR, "Token已失效, 可能账号信息已更改, 请重新获取Token");
        }
        return true;
    }
}


