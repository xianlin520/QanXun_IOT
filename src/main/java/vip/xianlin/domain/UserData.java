package vip.xianlin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class UserData {
    @TableId(value = "id", type = IdType.AUTO) // 设置id策略为自增
    private Integer id; // 用户ID, 数据库自增, 主键
    private String name; //  用户昵称
    private String account; // 用户邮箱账号
    private String password;    // 用户密码
    private String portrait; // 用户头像(base64)
    
    public UserData(String account, String password) {
        this.account = account;
        this.password = password;
    }
    
    public UserData(String name, String account, String password, String portrait) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.portrait = portrait;
    }
    
    public UserData() {
    }
}
