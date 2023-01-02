package vip.xianlin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xianlin.dao.ArticleDao;
import vip.xianlin.dao.UserArticleDao;
import vip.xianlin.domain.ArticleData;
import vip.xianlin.domain.UserArticleData;
import vip.xianlin.domain.UserData;

import java.util.List;
import java.util.Map;

@Service // 标注为Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
    
    @Autowired
    private UserArticleDao userArticleDao;
    
    public List<Map<String, Object>> queryArticleAndUserByID(Integer id) {
        articleDao.addViewCount(id);
        return articleDao.queryArticleAndUserByID(id);
    }
    
    public void addUserArticleData(UserArticleData userArticleData) {
        // 判断某列数据是否存在
        QueryWrapper<UserArticleData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userArticleData.getUserId());
        queryWrapper.eq("article_id", userArticleData.getArticleId());
        List<UserArticleData> count = userArticleDao.selectList(queryWrapper);
        if (count.size()!=0) {
            // 存在，更改
            UpdateWrapper<UserArticleData> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", count.get(0).getId());
            if (userArticleData.getLikeCount()!=null)updateWrapper.set("like_count", userArticleData.getLikeCount());
            if (userArticleData.getCollectCount()!=null)updateWrapper.set("collect_count", userArticleData.getCollectCount());
            userArticleDao.update(null, updateWrapper);
        } else {
            // 不存在，新增
            userArticleDao.insert(userArticleData);
        }
    }
    
    public ArticleData queryByID(Integer id) {
        return articleDao.selectById(id); // 查询后返回
    }
    
    public List<ArticleData> queryArticleListByUserID(int id) {
        QueryWrapper<ArticleData> queryUser = new QueryWrapper<>();    // 创建查询封装类, 并指定数据类
        queryUser.eq("user_key", id);   // 传入查询列和查询数据
        List<ArticleData> articleDataList =articleDao.selectList(queryUser); // 查询数据库, 并接收返回数据
        return articleDataList;
    }
    
    public boolean setData(ArticleData articleData) {
        int i = articleDao.updateById(articleData);
        return i != 0; // 如果返回数量大于0则返回true, 否则返回false
    }
    
    public void addData(ArticleData articleData) {
        articleDao.insert(articleData);
    }
}
