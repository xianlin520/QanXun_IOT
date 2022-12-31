package vip.xianlin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import vip.xianlin.controller.util.Code;
import vip.xianlin.controller.util.Result;
import vip.xianlin.util.CreateVerifiCodeImage;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController // 标记为控制类
@CrossOrigin // 解决跨域问题
@RequestMapping("token") // 指定请求路径
@Slf4j // 启用日志
public class TokenController {
    
    @Value("${logPath}")
    private String logPath;
    
    @GetMapping
    public String token() {
        return "token";
    }
    
    // 获取图片验证码
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // 获取日志文件列表
    @GetMapping("/getlogsfilelist")
    public Result getLogsFileList() {
        File file = new File(logPath);
        File[] files = file.listFiles();
        return new Result(files);
    }
    
    // 根据文件名返回日志内容
    @GetMapping("/getlogdata/{logFilePath}")
    public String getLogsFileData(@PathVariable String logFilePath) throws Exception {
        logFilePath = logPath+"/"+ logFilePath;
//        log.info(logpath);
        try {
            logFilePath = new String(Files.readAllBytes(Paths.get(logFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logFilePath;
    }
    
    @GetMapping("/logs")
    public RedirectView getLogsHtml() {
        return new RedirectView("/logs/logsfilelist.html");
    }
}
