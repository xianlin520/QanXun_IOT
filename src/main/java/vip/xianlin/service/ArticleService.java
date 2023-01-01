package vip.xianlin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xianlin.dao.ArticleDao;
import vip.xianlin.domain.ArticleData;
import vip.xianlin.domain.UserData;

import java.util.List;

@Service // 标注为Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
    
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
