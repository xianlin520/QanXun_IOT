package vip.xianlin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.xianlin.controller.util.Code;
import vip.xianlin.controller.util.Result;
import vip.xianlin.domain.ArticleData;
import vip.xianlin.domain.UserArticleData;
import vip.xianlin.domain.UserData;
import vip.xianlin.service.ArticleService;
import vip.xianlin.service.UserArticleService;
import vip.xianlin.service.UserService;
import vip.xianlin.util.JwtUtil;
import vip.xianlin.vo.ArticleDataVo;
import vip.xianlin.vo.UserDataVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController // 标记为控制类
@CrossOrigin // 解决跨域问题
@RequestMapping("article") // 指定请求路径
@Slf4j // 启用日志
public class ArticleController {
    
    @Autowired
    ArticleService articleService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserArticleService userArticleService;
    
    /**
     * 传入用户收藏信息, 更新用户收藏信息
     * @return 返回更新结果
     */
    @PutMapping("/upuserarticle")
    public Result upUserArticleData(@RequestBody UserArticleData userArticleData, HttpServletRequest res) {
        Integer userId = (Integer) Objects.requireNonNull(JwtUtil.getInfo(res.getHeader("Authorization"))).get("id"); // 取出请求头内Token, 并获取id
        userArticleData.setUserId(userId);
        try {
            articleService.addUserArticleData(userArticleData);
            log.info("文章收藏数据更新成功"+userArticleData);
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
    
    /**
     * 分页查询项目信息
     * @param pageNum 当前页
     * @param pageSize 每页数量
     * @return 返回查询结果
     */
    @GetMapping("/read/page/{pageSize}/{pageNum}")
    public Result queryArticleDataByPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize) {
        IPage<ArticleData> articleDataIPage = articleService.queryArticleDataByPage(pageNum, pageSize);
        return new Result(articleDataIPage);
    }
    
    /**
     * 传入文章ID, 获取文章信息
     * @return 返回结果
     */
    @GetMapping("/article/{id}")
    public Result getArticle(@PathVariable Integer id, HttpServletRequest res) {
        articleService.upDataArticleLike(id);
        Integer userId = Integer.valueOf(Objects.requireNonNull(JwtUtil.getInfo(res.getHeader("Authorization"))).get("id").toString()); // 取出请求头内Token, 并获取id
        ArticleData articleData = articleService.queryByID(id);
        UserData userData = userService.queryUserByID(articleData.getUserKey());
        UserArticleData userArticleData = userArticleService.selectById(id, userId);
        
        UserDataVo userDataVo = new UserDataVo();
        BeanUtils.copyProperties(userData, userDataVo);
        
        ArticleDataVo articleDataVo = new ArticleDataVo(articleData, userDataVo, userArticleData);
        return new Result(articleDataVo);
//        List<JSONObject> articleUserData = articleService.queryArticleAndUserByID(id);
//        ArticleData articleData = articleService.queryArticleByID(id);
//        return new Result(articleUserData.get(0));
    }
    
    /**
     * 传入文章id, 返回文章信息, 作者信息, 用户收藏信息
     * 如果用户未登录, 则返回用户收藏信息为null
     * @param id 文章id
     * @param res 请求头
     * @return 返回文章信息, 作者信息, 用户收藏信息
     */
    @GetMapping("/read/{id}")
    public Result getArticleData(@PathVariable Integer id, HttpServletRequest res) {
        // 取出请求头内Token, 用于判断是否登录
        Integer userId = Optional.ofNullable(res.getHeader("Authorization")) // 判断请求头是否存在Authorization, 并取出, 转换为Optional<String>
                .map(JwtUtil::getInfo)// 取出请求头内Token, 并解析为Map<String, Object>
                .map(info -> info.get("id"))// 取出Map内id(Object)
                .map(Object::toString)// 转换为String
                .map(Integer::valueOf)// 转换为Integer
                .orElse(null);// 以上任何一步为空, 则返回null
        // 查询文章信息
        ArticleDataVo articleData = articleService.getArticleData(id, userId);
        if (articleData == null) {
            return new Result(Code.NULL, "文章不存在");
        }
        return new Result(articleData);
    }
    
    @GetMapping("/read/fuzzy/{data}/{pageNum}")
    public Result fuzzyQueryArticlePage(@PathVariable String data, @PathVariable Integer pageNum) {
        IPage<ArticleData> articleDataList = articleService.fuzzyQueryArticlePage(data, pageNum);
        return new Result(articleDataList);
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
            log.info("文章添加失败, 数据不全");
            return new Result(Code.BUSINESS_ERR, (Object) "信息不全");
        }
        String userId = Objects.requireNonNull(JwtUtil.getInfo(res.getHeader("Authorization"))).get("id").toString(); // 取出请求头内Token, 并获取id
        ArticleData putData = new ArticleData(title, cover, category, content);
        putData.setUserKey(Integer.valueOf(userId)); // 将取出的userId转为int整形, 存入UserKey做项目外键
        articleService.addData(putData);
        log.info("文章添加成功");
        return new Result(Code.OK);
    }
    
}
