package vip.xianlin.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import vip.xianlin.domain.ArticleData;
import vip.xianlin.domain.ProjectData;
import vip.xianlin.vo.ArticleDataVo;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleDao extends BaseMapper<ArticleData> {
    @Select("SELECT a.*, u.*, ua.*\n" +
            "FROM t_article a\n" +
            "         LEFT JOIN t_user u ON a.id = u.id\n" +
            "         LEFT JOIN t_user_article ua ON a.id = ua.article_id\n" +
            "WHERE a.id = #{id}")
    List<JSONObject> queryArticleByID(@PathParam("id") Integer id);
    
    // 此接口会对文章数据进行模糊查询
    
    /**
     * 对文章数据进行查询, 并分页返回, 页码大小默认5
     * @param page 页码
     * @param queryString 查询字符串
     * @return 返回分页数据
     */
    @Select("SELECT * FROM t_article WHERE title LIKE concat('%',#{queryString},'%')")
    IPage<ArticleData> selectArticlesByTitle(IPage<ArticleData> page, String queryString);
    
    
    // 此接口会先查询对应文章id的喜欢数和收藏数, 然后写入到文章表对应列中
    /**
     * 更新文章的喜欢数和收藏数
     * @param id 文章id
     */
    @Update("UPDATE t_article a, \n" +
            "(SELECT SUM(like_count) as like_count, SUM(collect_count) as collect_count \n" +
            "FROM t_user_article \n" +
            "WHERE article_id = #{id}) b \n" +
            "SET a.like_count = b.like_count, a.collect_count = b.collect_count \n" +
            "WHERE a.id = #{id};")
    void upDataArticleLike(@PathParam("id") Integer id);
    
    // 此接口用于将阅读列自增1
//    @Update("UPDATE t_article SET t_article.view_count = t_article.view_count + 1 WHERE t_article.id = #{id}")
//    void addViewCount(@PathParam("id") Integer id);
    
    @Update("UPDATE t_article SET t_article.view_count = t_article.view_count + 1 WHERE t_article.id = #{id}")
    void addViewCount(@PathParam("id") Integer id);
    
    
}
