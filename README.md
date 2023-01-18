# QanXun_IOT - 千寻云物联平台

> ## 开发环境
> - 开发语言:  [Java (JDK1.8)](https://www.java.com/)
> - 主体框架: [Spring Boot 2 ](https://spring.io/projects/spring-boot)
> - 数据库框架: [Mybatis](https://mybatis.net.cn/) | [Mybatis-Plus](https://baomidou.com)
> - 日志框架: [Logback(SpringBoot默认框架)](https://logback.qos.ch/)
> - 部署环境: [Tomcat](https://www.apache.org/tomcat/), [Java]()

---
需要配置环境: MySql, Redis, smtp邮箱授权码
- 环境配置均在application.yml中配置

---
### 2023年1月18日 - 完成文章接口
- 文章接口完成
  - 文章联表查询
  - 文章分页查询
  - 文章详情查询
  - 文章新增
- 兼容mybatis-plus和mybatis
---
### 2023年1月1日 - 更新功能
- 完成项目接口
- 完善日志信息
- 完善异常捕获
- 新增日志查看页面

--- 
### 2022年10月11日 - 完成用户接口
- 完成用户注册接口
- 完成用户注册头像上传
- 完成所有用户操作接口
---
### 2022年10月10日 - 构建项目
 - 构建SpringBoot2基本项目结构 
 - 完成用户账号查询接口
 - 完成用户登录接口
 - 完成Email服务类(用于发送邮箱验证码)
 - 完成Redis服务类(用于缓存邮箱验证码)