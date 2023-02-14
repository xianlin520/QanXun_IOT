package vip.xianlin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xianlin.dao.DevicesDao;
import vip.xianlin.domain.DevicesData;

import java.util.List;

@Slf4j
@Service
public class DevicesService {
    @Autowired
    private DevicesDao devicesDao;
    
    // 根据数据, 使用MybatisPlus插入数据
    
    /**
     * 插入数据
     *
     * @param devicesData 设备信息数据
     * @return 是否插入成功(成功返回true, 失败返回false)
     */
    public Boolean insertDevicesData(DevicesData devicesData, Integer userId) {
        if (devicesData == null) {
            return false;
        }
        devicesData.setUserKey(userId);
        // 捕获异常
        try {
            devicesDao.insert(devicesData);
            return true;
        } catch (Exception e) {
            log.error("插入数据失败, 数据: {}", devicesData);
            return false;
        }
    }
    
    /**
     * 根据用户ID获取设备信息
     *
     * @param userId 用户ID
     * @return 设备信息列表
     */
    public List<DevicesData> getDevicesDataForUser(Integer userId) {
        return devicesDao.selectList(
                new QueryWrapper<DevicesData>().eq("user_key", userId)
        );
    }
    
    
}
