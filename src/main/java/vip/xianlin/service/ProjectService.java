package vip.xianlin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xianlin.dao.ProjectDao;
import vip.xianlin.domain.ProjectData;

import java.util.List;

@Service    // 标注为Service
public class ProjectService {
    @Autowired
    private ProjectDao projectDao;
    
    public ProjectData queryProjectByID(Integer id) {
        return projectDao.selectById(id); // 查询后返回
    }
    
    // 根据用户ID查询所有项目数据
    public List<ProjectData> queryProjectByUserKey(Integer userKey) {
        QueryWrapper<ProjectData> queryWrapper = new QueryWrapper<>(); // 创建查询封装类
        queryWrapper.eq("user_key", userKey);  // 传入查询数据
        List<ProjectData> projectData = projectDao.selectList(queryWrapper);    // 查询数据
        if (projectData.size() == 0) return null;   // 判断是否为空
        return projectData;  // 返回数据
    }
    
    // 插入项目数据, 并返回所有项目数据
    public List<ProjectData> addProjectData(ProjectData projectData) {
        projectDao.insert(projectData); // 插入数据
        return queryProjectByUserKey(projectData.getUserKey());  // 返回查询到的列表数据
    }
}
