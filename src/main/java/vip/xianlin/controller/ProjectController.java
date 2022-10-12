package vip.xianlin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.xianlin.controller.util.Code;
import vip.xianlin.controller.util.Result;
import vip.xianlin.domain.ProjectData;
import vip.xianlin.service.ProjectService;
import vip.xianlin.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController // 标记为控制类
@CrossOrigin // 解决跨域问题
@RequestMapping("project") // 指定请求路径
@Slf4j // 启用日志
public class ProjectController {
    @Autowired
    ProjectService projectService; // 注入项目服务类
    
    // 根据项目ID查询单个项目
    @GetMapping("/{id}")
    public Result queryProjectByID(@PathVariable Integer id) {
        ProjectData projectData = projectService.queryProjectByID(id);
        return new Result(projectData);
    }
    
    // 使用Put添加项目数据, 并返回用户名下所有项目
    @PutMapping
    public Result addProjectData(@RequestBody ProjectData projectData, HttpServletRequest res) {
        try {
            String userId = Objects.requireNonNull(JwtUtil.getInfo(res.getHeader("Authorization"))).get("id").toString(); // 取出请求头内Token, 并获取id
            projectData.setUserKey(Integer.valueOf(userId)); // 将取出的userId转为int整形, 存入UserKey做项目外键
            List<ProjectData> projectDataList = projectService.addProjectData(projectData); // 存入数据库, 并查询返回用户名下所有项目
            return new Result(projectDataList); // 返回数据
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return new Result(Code.SQLERR, (Object) "数据插入失败");
        }
    }
    
    
}
