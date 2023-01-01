package vip.xianlin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import vip.xianlin.domain.ArticleData;
import vip.xianlin.domain.ProjectData;

@Mapper
public interface ArticleDao extends BaseMapper<ArticleData> {

}
