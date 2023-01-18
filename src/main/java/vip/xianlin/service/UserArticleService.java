package vip.xianlin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xianlin.dao.UserArticleDao;
import vip.xianlin.domain.UserArticleData;

@Service
public class UserArticleService {
    @Autowired
    private UserArticleDao userArticleDao;
    
    public UserArticleData selectById(Integer articleId, Integer userId) {
        return userArticleDao.selectArticleLike(articleId, userId);
    }
}
