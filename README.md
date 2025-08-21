# 苍穹外卖（Sky Take-Out）

## 项目简介

苍穹外卖是一套基于 Spring Boot、MyBatis、Redis、MySQL 等技术栈开发的外卖点餐系统，包含用户端、商家端、后台管理等功能模块，适合学习和二次开发。

## 目录结构

```
sky-take-out/
├── sky-common/   # 公共模块
├── sky-pojo/     # 实体与DTO模块
├── sky-server/   # 服务端主模块
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/sky/   # 业务代码
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       ├── application-dev.yml
│   │   │       ├── mapper/     # MyBatis XML
│   │   │       └── template/   # 报表模板等
│   │   └── test/
│   │       └── java/com/sky/   # 测试代码
│   └── pom.xml
└── pom.xml
```

## 技术栈

- Spring Boot
- MyBatis
- Druid 数据库连接池
- Redis
- MySQL
- Maven

## 快速启动

1. **环境准备**
   - JDK 21+
   - Maven 3.6+
   - MySQL 8.0
   - Redis

2. **数据库初始化**
   - 创建数据库并导入表结构和初始数据（SQL 脚本请参考项目文档或 resources 目录）。

3. **配置修改**
   - 修改 `sky-server/src/main/resources/application.yml` 和 `application-dev.yml`，配置数据库和 Redis 连接信息。

4. **编译与运行**
   ```bash
   cd sky-take-out
   mvn clean package
   cd sky-server
   mvn spring-boot:run
   ```

5. **访问系统**
   - 后台管理端、用户端等访问地址请参考具体前端项目或接口文档。

## 其他说明

- MyBatis Mapper XML 文件位于 `sky-server/src/main/resources/mapper/`。
- 报表模板等资源位于 `sky-server/src/main/resources/template/`。
- 日志、环境等配置可在 `application.yml` 中调整。

