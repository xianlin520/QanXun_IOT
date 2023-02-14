package vip.xianlin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xianlin.dao.ArticleDao;
import vip.xianlin.dao.UserArticleDao;
import vip.xianlin.domain.ArticleData;
import vip.xianlin.domain.UserArticleData;
import vip.xianlin.domain.UserData;
import vip.xianlin.vo.ArticleDataVo;
import vip.xianlin.vo.UserDataVo;

import java.util.List;
import java.util.Optional;

@Service // 标注为Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
    
    @Autowired
    private UserArticleDao userArticleDao;
    
    @Autowired
    private UserArticleService userArticleService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 传入文章id, 返回文章信息, 作者信息, 用户收藏信息
     * 如果userId为null, 用户收藏信息为null
     *
     * @param id     文章id
     * @param userId 用户id
     * @return 文章信息, 作者信息, 用户收藏信息
     */
    public ArticleDataVo getArticleData(Integer id, Integer userId) {
        // 更新文章浏览量, 喜欢量, 收藏量
        upDataArticleLike(id);
        
        // 进行数据库查询
        ArticleData articleData = queryByID(id);
        if (articleData == null) {
            return null;
        }
        // 查询作者信息
        UserData userData = userService.queryUserByID(articleData.getUserKey());
        
        // 查询用户收藏信息, 如果用户未登录, 则返回用户收藏信息为null
        UserDataVo userDataVo = new UserDataVo(userData);
        
        // 查询用户收藏信息, 如果用户未登录, 则返回用户收藏信息为null
        UserArticleData userArticleData = Optional.ofNullable(userId)
                .map(uid -> userArticleService.selectById(id, uid))
                .orElse(null);
//        if (userId!=null) {
//            userArticleData = userArticleService.selectById(id, userId);
//        }
        
        // 将查询信息封装到Vo中
        return new ArticleDataVo(articleData, userDataVo, userArticleData);
    }
    
    // 分页查询
    public IPage<ArticleData> queryArticleDataByPage(Integer pageNum, Integer pageSize) {
        Page<ArticleData> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ArticleData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(ArticleData::getId, ArticleData::getTitle, ArticleData::getPublishTime, ArticleData::getCategory, ArticleData::getCover, ArticleData::getTitleTag, ArticleData::getIsRecommend, ArticleData::getViewCount, ArticleData::getLikeCount, ArticleData::getCollectCount, ArticleData::getUserKey);
        // 使用mybatisPlus的分页查询
        return articleDao.selectPage(page, lambdaQueryWrapper);
    }
    
    /**
     * 查询指定字符串的文章, 并分页
     *
     * @param data    查询字符串
     * @param pageNum 当前页
     * @return 返回查询结果
     */
    public IPage<ArticleData> fuzzyQueryArticlePage(String data, Integer pageNum) {
        Page<ArticleData> page = new Page<>(pageNum, 5); // 当前页码为 1，每页大小为 10
        return articleDao.selectPage(page,
                new QueryWrapper<ArticleData>().lambda()
                        .like(ArticleData::getTitle, data));
    }
    
    /*// 文章信息和对应的作者信息, 并一起返回
    public List<Map<String, Object>> queryArticleAndUserByID(Integer id) {
        return articleDao.queryArticleAndUserByID(id);
    }*/
    
    // 此接口会先查询对应文章id的喜欢数和收藏数, 然后写入到文章表对应列中
    
    /**
     * 指定文章浏览量+1
     * 更新指定文章的喜欢数和收藏数
     *
     * @param id 文章id
     */
    public void upDataArticleLike(Integer id) {
        articleDao.addViewCount(id); // 阅读数+1
        try {
            articleDao.upDataArticleLike(id);
        } catch (Exception e) {
//            throw new RuntimeException(e);
        }
    }
    
    // 作者的所有文章
    public List<ArticleData> queryArticleListByUserID(int id) {
        LambdaQueryWrapper<ArticleData> queryUser = new LambdaQueryWrapper<>();    // 创建查询封装类, 并指定数据类
        queryUser.eq(ArticleData::getId, id);   // 传入查询列和查询数据
        return articleDao.selectList(queryUser);
    }
    
    // 新增喜欢/收藏信息
    public void addUserArticleData(UserArticleData userArticleData) {
        // 判断某列数据是否存在
        LambdaQueryWrapper<UserArticleData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserArticleData::getUserId, userArticleData.getUserId());
        queryWrapper.eq(UserArticleData::getArticleId, userArticleData.getArticleId());
        List<UserArticleData> count = userArticleDao.selectList(queryWrapper);
        if (count.size() != 0) {
            // 存在，更改
            LambdaUpdateWrapper<UserArticleData> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(UserArticleData::getId, count.get(0).getId());
            if (userArticleData.getLikeCount() != null)
                updateWrapper.set(UserArticleData::getLikeCount, userArticleData.getLikeCount());
            if (userArticleData.getCollectCount() != null)
                updateWrapper.set(UserArticleData::getCollectCount, userArticleData.getCollectCount());
            userArticleDao.update(null, updateWrapper);
            articleDao.upDataArticleLike(count.get(0).getArticleId());
        } else {
            // 不存在，新增
            userArticleDao.insert(userArticleData);
            articleDao.upDataArticleLike(count.get(0).getArticleId());
        }
    }
    
    
    public boolean setData(ArticleData articleData) {
        int i = articleDao.updateById(articleData);
        return i != 0; // 如果返回数量大于0则返回true, 否则返回false
    }
    
    public ArticleData queryByID(Integer id) {
        return articleDao.selectById(id); // 查询后返回
    }
    
    public void addData(ArticleData articleData) {
        articleDao.insert(articleData);
    }
}
