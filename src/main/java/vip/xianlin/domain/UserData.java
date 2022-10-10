package vip.xianlin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class UserData {
    private Integer id;
    private String name;
    private String account;
    private String password;
}
