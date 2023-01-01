package vip.xianlin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xianlin.dao.ArticleDao;
import vip.xianlin.domain.ArticleData;
import vip.xianlin.domain.UserData;

@Service // 标注为Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
    
    public ArticleData queryByID(Integer id) {
        return articleDao.selectById(id); // 查询后返回
    }
    
    
    public void addData(ArticleData articleData) {
        articleDao.insert(articleData);
    }
}
