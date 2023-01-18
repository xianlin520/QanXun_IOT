package vip.xianlin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_article")
public class ArticleData {
    // !=必须服务端/管理员控制, -=可选参数, *=必填
    @TableId(value = "id", type = IdType.AUTO)  // 设置id策略为自增
    private Integer id; // 文章id !
    private String title; //标题 *
    private Date publishTime; //创建时间 !
    private String cover; //文章封面 *
    private Integer viewCount; // 阅读数 !
    private Integer likeCount; // 喜欢数 !
    private Integer collectCount; // 收藏数 !
    private String titleTag; // 文章标签 !
    private String category; // 文章分类 *
    private Boolean isRecommend; // 是否推荐 !
    private Integer userKey; // 用户id !
    private String content; // 文章内容 *
    
    public ArticleData() {
    }
    
    public ArticleData(String title, String cover, String category, String content) {
        this.title = title;
        this.cover = cover;
        this.category = category;
        this.content = content;
    }
}
