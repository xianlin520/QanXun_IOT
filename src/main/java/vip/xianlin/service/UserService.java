package vip.xianlin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xianlin.dao.UserDao;
import vip.xianlin.domain.UserData;

import java.util.List;

@Service // 标注为Service
public class UserService {
    @Autowired
    private UserDao userDao;
    
    
    public UserData queryUserByID(Integer id) {
        return userDao.selectById(id); // 查询后返回
    }
    
    // 根据传入的Account账号信息, 查询数据库内数据, 如果账号已存在则返回true, 否则返回false;
    public Boolean queryAccount(String account) {
        QueryWrapper<UserData> queryUser = new QueryWrapper<>();    // 创建查询封装类, 并指定数据类
        queryUser.eq("account", account);   // 传入查询列和查询数据
        List<UserData> userData = userDao.selectList(queryUser); // 查询数据库, 并接收返回数据
        return userData.size() != 0;  // 判断数据是否为空, 为空返回false
// 不为空返回true, 表示用户已存在
    }
    
    // 根据传入的Account账号信息和 Password密码信息, 查询数据库内数据, 如果查询到则返回, 否则返回null
    public UserData queryAccountAndPassword(UserData userData) {
        QueryWrapper<UserData> queryWrapper = new QueryWrapper<>(); // 创建查询封装类, 并指定数据类
        queryWrapper.eq("account", userData.getAccount());  // 传入账号
        queryWrapper.eq("password", userData.getPassword());    //传入密码
        List<UserData> retUserData = userDao.selectList(queryWrapper); // 查询数据, 并接收返回数据
        if (retUserData.size() == 0) return null; // 如果未查询到数据, 则返回null
        return retUserData.get(0); // 如果查询到数据, 就返回数据
    }
    
    // 根据传入的用户对象, 插入到数据库
    public void addUserData(UserData userData) {
        userDao.insert(userData); // 如果数据内有重复数据, 或插入失败则会报错, 表示添加失败
    }
    
    // 根据传入的用户id, 和用户对象, 修改数据库信息
    public boolean updateById(Integer id, UserData userData) {
        userData.setId(id); // 设置更改用户id
        int i = userDao.updateById(userData); // 根据用户id和对象内数据更改数据
        return i != 0; // 如果返回数量大于0则返回true, 否则返回false
    }
    
}
