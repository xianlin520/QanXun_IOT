package vip.xianlin.service;

import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class VerificationCodeService {
    private final int generateVerificationCodeLength=4; //生成验证码的位数
    
    private final String[] metaCode={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}; // 验证码内容范围
    
    //  生成验证码
    public String generateVerificationCode() {
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        while (verificationCode.length()<generateVerificationCodeLength){
            int i = random.nextInt(metaCode.length);
            verificationCode.append(metaCode[i]);
        }
        return verificationCode.toString(); // 返回生成的验证码
    }
}
