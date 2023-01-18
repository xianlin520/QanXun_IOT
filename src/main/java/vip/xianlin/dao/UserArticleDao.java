package vip.xianlin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;
import vip.xianlin.domain.UserArticleData;

@Mapper // 使用工厂代理创建对象, 基础BaseMapper
public interface UserArticleDao extends BaseMapper<UserArticleData> {
    
    
    @Select("SELECT * FROM t_user_article WHERE article_id = #{articleId} AND user_id = #{userId}")
    UserArticleData selectArticleLike(@PathVariable Integer articleId, @PathVariable Integer userId);
    
}
