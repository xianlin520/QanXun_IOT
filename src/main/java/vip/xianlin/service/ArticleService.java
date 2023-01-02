package vip.xianlin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
    
    // 文章信息和对应的作者信息, 并一起返回
    public List<Map<String, Object>> queryArticleAndUserByID(Integer id) {
        articleDao.addViewCount(id);
        return articleDao.queryArticleAndUserByID(id);
    }
    // 此接口会先查询对应文章id的喜欢数和收藏数, 然后写入到文章表对应列中
    public void upDataArticleLike(Integer id) {
        articleDao.upDataArticleLike(id);
    }
    
    // 作者的所有文章
    public List<ArticleData> queryArticleListByUserID(int id) {
        LambdaQueryWrapper<ArticleData> queryUser = new LambdaQueryWrapper<>();    // 创建查询封装类, 并指定数据类
        queryUser.eq(ArticleData::getId, id);   // 传入查询列和查询数据
        List<ArticleData> articleDataList =articleDao.selectList(queryUser); // 查询数据库, 并接收返回数据
        return articleDataList;
    }
    // 新增喜欢/收藏信息
    public void addUserArticleData(UserArticleData userArticleData) {
        // 判断某列数据是否存在
        LambdaQueryWrapper<UserArticleData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserArticleData::getUserId, userArticleData.getUserId());
        queryWrapper.eq(UserArticleData::getArticleId, userArticleData.getArticleId());
        List<UserArticleData> count = userArticleDao.selectList(queryWrapper);
        if (count.size()!=0) {
            // 存在，更改
            LambdaUpdateWrapper<UserArticleData> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(UserArticleData::getId, count.get(0).getId());
            if (userArticleData.getLikeCount()!=null)updateWrapper.set(UserArticleData::getLikeCount, userArticleData.getLikeCount());
            if (userArticleData.getCollectCount()!=null)updateWrapper.set(UserArticleData::getCollectCount, userArticleData.getCollectCount());
            userArticleDao.update(null, updateWrapper);
        } else {
            // 不存在，新增
            userArticleDao.insert(userArticleData);
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
