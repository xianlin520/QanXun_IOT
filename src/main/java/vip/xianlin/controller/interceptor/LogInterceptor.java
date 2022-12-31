package vip.xianlin.controller.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取访问者IP
        String ipAddress = request.getRemoteAddr();
        // 获取访问者访问的路径
        String path = request.getRequestURI();
        log.info("发现访问, IP：{}, 访问路径[{}],  ", ipAddress, path);
        return true;
    }
}
