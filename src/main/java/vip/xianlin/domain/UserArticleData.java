package vip.xianlin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@TableName("t_user_article")
public class UserArticleData {
    @JsonIgnore // 此注解可忽略id字段, 前端就算传入也不会解析
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private Integer userId;
    private Integer articleId;
    private Boolean likeCount;
    private Boolean collectCount;
}
