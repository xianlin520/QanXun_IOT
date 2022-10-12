package vip.xianlin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import vip.xianlin.domain.ProjectData;

@Mapper // 使用工厂代理创建对象, 基础BaseMapper
public interface ProjectDao extends BaseMapper<ProjectData> {

}
