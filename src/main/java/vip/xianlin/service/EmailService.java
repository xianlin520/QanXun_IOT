package vip.xianlin.service;

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
    public void sendEmailVerificationCode(String toAddress) {
        //调用 VerificationCodeService 生产验证码
        String verifyCode = verificationCodeService.generateVerificationCode();
    
        //创建邮件正文
        Context context = new Context();
        context.setVariable("verifyCode", Arrays.asList(verifyCode.split("")));
    
        //将模块引擎内容解析成html字符串
        String emailContent = templateEngine.process("EmailVerificationCode", context);
        MimeMessage message=mailSender.createMimeMessage();
        try {
            nick=javax.mail.internet.MimeUtility.encodeText("千寻云"); // 设置发件人昵称
            
            //true表示需要创建一个multipart message
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
//            helper.setFrom(fromAddress);
            helper.setFrom(new InternetAddress(nick+" <"+fromAddress+">"));
            
            helper.setTo(toAddress);
            helper.setSubject("千寻云-注册验证码");
            helper.setText(emailContent,true);
            mailSender.send(message);
        }catch (Exception e) {
            log.error("邮件验证码发送失败");
            log.error(String.valueOf(e));
        }
    }
}

