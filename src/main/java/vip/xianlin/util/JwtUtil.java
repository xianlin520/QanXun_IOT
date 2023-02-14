package vip.xianlin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import vip.xianlin.domain.UserData;
import vip.xianlin.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

// Jwt加密类
public class JwtUtil {
    // 设置过期时间, 单位毫秒
    private static final long EXPIRE_TIME = 1440 * 60 * 1000; // 24小时
    private static final String SECRET = "XianLin";   // Jwt私钥(加密解密都需要)
    
    
    /**
     * 根据请求头内的Token, 获取Token内的用户ID
     *
     * @param res 传入请求头
     * @return 返回用户ID(userID)
     */
    public static Integer getIdFormRes(HttpServletRequest res) {
        // 取出请求头内Token, 用于判断是否登录
        return Optional.ofNullable(res.getHeader("Authorization")) // 判断请求头是否存在Authorization, 并取出, 转换为Optional<String>
                .map(JwtUtil::getInfo)// 取出请求头内Token, 并解析为Map<String, Object>
                .map(info -> info.get("id"))// 取出Map内id(Object)
                .map(Object::toString)// 转换为String
                .map(Integer::valueOf)// 转换为Integer
                .orElse(null);
    }
    
    // 传入Token, 根据Token内的信息去数据库内查询, 如果查询到就返回true, 否则返回false
    
    /**
     * 传入token, 根据token中的用户id, 查询数据库
     *
     * @param token 传入token
     * @return 返回查询结果(是否存在)
     */
    public static boolean checkSignByToken(String token) {
        try {
            Map<String, Object> userInfo = getInfo(token);
            Integer id = (Integer) userInfo.get("id");
            UserService userService = BeanUtil.getBean(UserService.class);
            UserData userData = userService.queryUserByID(id); // 返回查询结果
            return userData != null;
        } catch (JWTVerificationException e) {
//            throw new RuntimeException("token 无效，请重新获取");
            return false;
        }
    }
    
    
    // 根据传入的时间信息生成Token
    public static String longSign(String userId, Map<String, Object> info, long time) {
        try {
            Date date = new Date(System.currentTimeMillis() + time); // 获取过期时间
            Algorithm algorithm = Algorithm.HMAC256(SECRET); // 获取秘钥
            return JWT.create()
                    .withAudience(userId)   //将userId保存到token里面
                    .withClaim("info", info)    //存放自定义数据
                    .withExpiresAt(date)    // 将过期时间存入
                    .sign(algorithm);   // 将私钥存入
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 生成jwt字符串，24小时后过期  JWT(json web token)
     *
     * @param userId                                                              用户ID
     * @param info,Map的value只能存放值的类型为：Map，List，Boolean，Integer，Long，Double，String and Date
     */
    public static String sign(String userId, Map<String, Object> info) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME); // 获取过期时间
            Algorithm algorithm = Algorithm.HMAC256(SECRET); // 获取秘钥
            return JWT.create()
                    .withAudience(userId)   //将userId保存到token里面
                    .withClaim("info", info)    //存放自定义数据
                    .withExpiresAt(date)    // 将过期时间存入
                    .sign(algorithm);   // 将私钥存入
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 校验token
     *
     * @param token jwt字符串
     * @return true:校验通过，false:校验失败
     */
    public static boolean checkSign(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    //.withClaim("username, username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
//            throw new RuntimeException("token 无效，请重新获取");
            return false;
        }
    }
    
    /**
     * 根据token获取userId
     *
     * @param token jwt字符串
     * @return
     */
    public static String getUserUuid(String token) {
        try {
            String userId = JWT.decode(token).getAudience().get(0);
            return userId;
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    
    /**
     * 根据token获取自定义数据info
     *
     * @param token jwt字符串
     * @return
     */
    public static Map<String, Object> getInfo(String token) {
        try {
            return JWT.decode(token).getClaim("info").asMap();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
