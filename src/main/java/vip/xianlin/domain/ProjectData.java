package vip.xianlin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data
@TableName("t_project")
public class ProjectData {
    @TableId(value = "id", type = IdType.AUTO)  // 设置id策略为自增
    private Integer id; // 项目id, 数据库自增, 主键
    private String name; // 项目名称
    private Byte industry; // 项目所属行业
    private Byte netWorkKind; //  项目网络类型, 联网方案
    private String projectTag= UUID.randomUUID().toString(); // 生成不重复UUID, 作为项目标识
    private Timestamp createDate = new Timestamp(new Date().getTime());    // 项目创建时间, 默认当前时间
    private String remark;  // 项目备注, 描述
    private Integer userKey;    // 用户主键, 项目外键
}
