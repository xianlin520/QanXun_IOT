package vip.xianlin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import vip.xianlin.domain.ArticleData;
import vip.xianlin.domain.ProjectData;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleDao extends BaseMapper<ArticleData> {
    // 此接口会查询文章信息和对应的作者信息, 并一起返回
    @Select("SELECT\n" +
            "    a.id AS article_id,\n" +
            "    a.title,\n" +
            "    a.publish_time,\n" +
            "    a.cover,\n" +
            "    a.view_count,\n" +
            "    a.like_count,\n" +
            "    a.collect_count,\n" +
            "    a.title_tag,\n" +
            "    a.category,\n" +
            "    a.is_recommend,\n" +
            "    u.id AS user_id,\n" +
            "    u.name,\n" +
            "    u.account,\n" +
            "    u.portrait\n" +
            "FROM\n" +
            "    t_article a\n" +
            "INNER JOIN t_user u ON a.user_key = u.id\n" +
            "WHERE\n" +
            "    a.id = #{id}\n")
    List<Map<String, Object>> queryArticleAndUserByID(@PathParam("id") Integer id);
    
    // 此接口会先查询对应文章id的喜欢数和收藏数, 然后写入到文章表对应列中
    @Update("UPDATE t_article a, \n" +
            "(SELECT SUM(like_count) as like_count, SUM(collect_count) as collect_count \n" +
            "FROM t_user_article \n" +
            "WHERE article_id = #{id}) b \n" +
            "SET a.like_count = b.like_count, a.collect_count = b.collect_count \n" +
            "WHERE a.id = #{id};")
    void upDataArticleLike(@PathParam("id") Integer id);
    
    // 此接口用于将阅读列自增1
    @Update("UPDATE t_article SET t_article.view_count = t_article.view_count + 1 WHERE t_article.id = #{id}")
    void addViewCount(@PathParam("id") Integer id);
    
    
}
