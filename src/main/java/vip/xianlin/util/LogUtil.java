package vip.xianlin.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j // 启用日志
public class LogUtil {
    
    public static void logResDataInfo(Object data, HttpServletRequest request) {
        // 获取访问者IP
        String ipAddress = request.getRemoteAddr();
        // 获取访问者访问的路径
        String path = request.getRequestURI();
        // 获取访问者访问的方式
        String method = request.getMethod();
        String token = request.getHeader("Authorization");
    
        log.info("\n=================================================\n" +
                "尝试访问, 访问路径[{}] 访问方式[{}], IP：{}\n" +
                "Token: {}\n" +
                "Body {}: \n" +
                "\n=================================================", path, method, ipAddress, token, data);
        
    }
}
