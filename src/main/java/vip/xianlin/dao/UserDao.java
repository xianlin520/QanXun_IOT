package vip.xianlin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import vip.xianlin.domain.UserData;

@Mapper // 使用工厂代理创建对象, 基础BaseMapper
public interface UserDao extends BaseMapper<UserData> {
    // 使用MybatisPlus代理生成SQL执行函数
    
}
