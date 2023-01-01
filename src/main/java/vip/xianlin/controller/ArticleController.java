package vip.xianlin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.xianlin.controller.util.Code;
import vip.xianlin.controller.util.Result;
import vip.xianlin.domain.ArticleData;
import vip.xianlin.service.ArticleService;
import vip.xianlin.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController // 标记为控制类
@CrossOrigin // 解决跨域问题
@RequestMapping("article") // 指定请求路径
@Slf4j // 启用日志
public class ArticleController {
    
    @Autowired
    ArticleService articleService;
    
    @GetMapping("/{id}")
    public Result getArticle(@PathVariable Integer id) {
        ArticleData articleData = articleService.queryByID(id);
        return new Result(articleData);
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
