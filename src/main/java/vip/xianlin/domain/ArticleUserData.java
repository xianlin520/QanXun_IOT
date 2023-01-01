package vip.xianlin.domain;

import lombok.Data;

@Data
public class ArticleUserData {
    private ArticleData articleData;
    private UserData userData;
    
    public ArticleUserData(ArticleData articleData, UserData userData) {
        this.articleData = articleData;
        this.userData = userData;
    }
}
