package vip.xianlin.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vip.xianlin.service.VerificationCodeService;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;


@Slf4j // 启用日志
@Component
@Service
public class EmailService {
    @Autowired
    private RedisService redisService; // 注入Redis数据库服务类
    
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private VerificationCodeService verificationCodeService;
    
    // 发件人邮箱地址
    @Value("${spring.mail.username}")
    private String fromAddress;
    //设置自定义发件人昵称
    String nick="";
    // 发送电子邮件验证码
    public Boolean sendEmailVerificationCode(String toAddress) {
        //调用 VerificationCodeService 生产验证码
        String verifyCode = verificationCodeService.generateVerificationCode();
        boolean flag = redisService.putData(toAddress, verifyCode); // 往Redis数据内存入 邮箱=>验证码, 时效五分钟 //TODO 在邮件验证码服务类里, 存入缓存数据库数据库
        if (!flag) {
            return false; // 方法返回false表示存入失败
        }
        //创建邮件正文
        Context context = new Context();
        context.setVariable("verifyCode", Arrays.asList(verifyCode.split("")));
    
        //将模块引擎内容解析成html字符串
        String emailContent = templateEngine.process("EmailVerificationCode", context);
        MimeMessage message=mailSender.createMimeMessage();
        try {
            nick=javax.mail.internet.MimeUtility.encodeText("千寻云"); //TODO 邮件验证码发件人昵称
            
            //true表示需要创建一个multipart message
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
//            helper.setFrom(fromAddress);
            helper.setFrom(new InternetAddress(nick+" <"+fromAddress+">"));
            
            helper.setTo(toAddress);
            helper.setSubject("千寻云-注册验证码"); //TODO 邮件验证码标题
            helper.setText(emailContent,true);
            mailSender.send(message);
            return true;
        }catch (Exception e) {
            log.error("邮件验证码发送失败");
            log.error(String.valueOf(e));
            redisService.delData(toAddress); // 如果发送失败则删除数据库内无效数据
            return false;
        }
    }
}

