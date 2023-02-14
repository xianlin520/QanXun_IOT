package vip.xianlin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@TableName("t_devices")
public class DevicesData {
    @JsonIgnore // 此注解可忽略id字段, 前端就算传入也不会解析
    @TableId(value = "id", type = IdType.AUTO) // 设置id策略为自增
    private Integer id; // 设备ID, 数据库自增, 主键
    private String name; // 设备名称
    private String uid; // 设备UID
    private String topic; // 设备主题
    private Integer userKey; // 设备所属用户ID
}
