package vip.xianlin.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import vip.xianlin.domain.ArticleData;
import vip.xianlin.domain.UserArticleData;

@Data
public class ArticleDataVo {
    // 文章信息
    private ArticleData articleData;
    
    // 作者信息
    @JsonProperty("author")
    private UserDataVo userDataVo;
    
    //文章的喜欢收藏数据表
    @JsonProperty("likeCollect")
    private UserArticleData userArticleData;
    
    public ArticleDataVo() {
    }
    
    public ArticleDataVo(ArticleData articleData, UserDataVo userDataVo, UserArticleData userArticleData) {
        this.articleData = articleData;
        this.userDataVo = userDataVo;
        this.userArticleData = userArticleData;
    }
    
    @Override
    public String toString() {
        return "ArticleDataVo{" +
                "articleData=" + articleData +
                ", userDataVo=" + userDataVo +
                ", userArticleData=" + userArticleData +
                '}';
    }
}