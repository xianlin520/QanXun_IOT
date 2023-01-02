package vip.xianlin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.xianlin.controller.util.Code;
import vip.xianlin.controller.util.Result;
import vip.xianlin.domain.ArticleData;
import vip.xianlin.domain.UserArticleData;
import vip.xianlin.domain.UserData;
import vip.xianlin.service.ArticleService;
import vip.xianlin.service.UserService;
import vip.xianlin.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController // 标记为控制类
@CrossOrigin // 解决跨域问题
@RequestMapping("article") // 指定请求路径
@Slf4j // 启用日志
public class ArticleController {
    
    @Autowired
    ArticleService articleService;
    
    @Autowired
    UserService userService;
    
    @PutMapping("/upuserarticle")
    public Result upUserArticleData(@RequestBody UserArticleData userArticleData, HttpServletRequest res) {
        Integer userId = (Integer) Objects.requireNonNull(JwtUtil.getInfo(res.getHeader("Authorization"))).get("id"); // 取出请求头内Token, 并获取id
        userArticleData.setUserId(userId);
        try {
            articleService.addUserArticleData(userArticleData);
            return new Result("成功");
        } catch (Exception e) {
            log.info("文章收藏添加失败"+userArticleData);
            return new Result(Code.SQLERR, (Object) "失败");
        }
    }
    
    @GetMapping("/list/{id}")
    public Result getUserArticleList(@PathVariable Integer id) {
        List<ArticleData> articleDataList = articleService.queryArticleListByUserID(id);
        return new Result(articleDataList);
    }
    
    @GetMapping("/read/{id}")
    public Result getArticle(@PathVariable Integer id) {
        List<Map<String, Object>> articleUserData = articleService.queryArticleAndUserByID(id);
        return new Result(articleUserData.get(0));
    }
    
    @PostMapping("/admin")
    public Result adminSetArticleInfo(@RequestBody ArticleData articleData, HttpServletRequest res) {
        Integer userId = (Integer) Objects.requireNonNull(JwtUtil.getInfo(res.getHeader("Authorization"))).get("id"); // 取出请求头内Token, 并获取id
        if (userId != 1) return new Result(Code.SQLERR, (Object) "权限不足");
        boolean b = articleService.setData(articleData);
        return new Result(b);
    }
    
    @PutMapping
    public Result addArticleData(@RequestBody ArticleData articleData, HttpServletRequest res) {
        String title = articleData.getTitle();
        String category = articleData.getCategory();
        String content = articleData.getContent();
        String cover = articleData.getCover();
        if (title == null || category == null || content == null||cover==null) {
            return new Result(Code.BUSINESS_ERR, (Object) "信息不全");
        }
        String userId = Objects.requireNonNull(JwtUtil.getInfo(res.getHeader("Authorization"))).get("id").toString(); // 取出请求头内Token, 并获取id
        ArticleData putData = new ArticleData(title, cover, category, content);
        putData.setUserKey(Integer.valueOf(userId)); // 将取出的userId转为int整形, 存入UserKey做项目外键
        articleService.addData(putData);
        return new Result(Code.OK);
    }
    
}
