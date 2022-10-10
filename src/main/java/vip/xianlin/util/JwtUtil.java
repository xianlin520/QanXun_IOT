package vip.xianlin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;
import java.util.Map;

// Jwt加密类
public class JwtUtil {
    // 设置过期时间, 单位毫秒
    private static final long EXPIRE_TIME = 1440 * 60 * 1000; // 24小时
    private static final String SECRET = "XianLin";   // Jwt私钥(加密解密都需要)
    
    /**
     * 生成jwt字符串，24小时后过期  JWT(json web token)
     * @param userId 用户ID
     * @param info,Map的value只能存放值的类型为：Map，List，Boolean，Integer，Long，Double，String and Date
     * */
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
     * @param token jwt字符串
     * @return true:校验通过，false:校验失败
     * */
    public static boolean checkSign(String token) {
        try {
            Algorithm algorithm  = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    //.withClaim("username, username)
                    .build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException e) {
//            throw new RuntimeException("token 无效，请重新获取");
            return false;
        }
    }
    
    /**
     * 根据token获取userId
     * @param token jwt字符串
     * @return
     * */
    public static String getUserId(String token) {
        try {
            String userId = JWT.decode(token).getAudience().get(0);
            return userId;
        }catch (JWTDecodeException e) {
            return null;
        }
    }
    
    /**
     * 根据token获取自定义数据info
     * @param token jwt字符串
     * @return
     * */
    public static Map<String, Object> getInfo(String token) {
        try {
            return JWT.decode(token).getClaim("info").asMap();
        }catch (JWTDecodeException e) {
            return null;
        }
    }
}
