package vip.xianlin.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import vip.xianlin.domain.UserData;

@Data
public class UserDataVo {
    private Integer id; // 用户ID, 数据库自增, 主键
    private String name; //  用户昵称
    private String account; // 用户邮箱账号
    private String portrait; // 用户头像(base64)
    
    public UserDataVo(UserData userData) {
        if (userData!=null) {
            this.id = userData.getId();
            this.name = userData.getName();
            this.account = userData.getAccount();
            this.portrait = userData.getPortrait();
        }
    }
    
    public UserDataVo() {
    }
}
