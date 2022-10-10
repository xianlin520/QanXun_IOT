package vip.xianlin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xianlin.dao.UserDao;

@Service // 标注为Service
public class UserService {
    @Autowired
    private UserDao userDao;
    
    
}
