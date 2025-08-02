# Spring Batch Demo - Docker 部署指南

## 前置条件

- Docker 已安装
- Docker Compose 已安装（可选）
- 项目已构建（存在 target/spring.batch.demo-0.0.1-SNAPSHOT.jar）

## 构建和运行

### 方法一：使用 Docker 命令

1. **构建镜像**

   ```bash
   # 如果还没有构建 JAR 文件，先构建项目
   mvn clean package -DskipTests

   # 构建 Docker 镜像
   docker build -t spring-batch-demo:latest .
   ```

2. **运行容器**

   ```bash
   # 前台运行
   docker run -p 8080:8080 spring-batch-demo:latest

   # 后台运行
   docker run -d -p 8080:8080 --name spring-batch-app spring-batch-demo:latest
   ```

### 方法二：使用构建脚本

```bash
# 使用提供的构建脚本
./build-docker.sh
```

### 方法三：使用 Docker Compose

```bash
# 构建并启动
docker-compose up --build

# 后台运行
docker-compose up -d --build

# 停止服务
docker-compose down
```

## Docker 镜像特性

- **基础镜像**: OpenJDK 17 JDK Slim
- **安全性**: 使用非 root 用户运行应用
- **健康检查**: 内置健康检查机制
- **端口**: 暴露 8080 端口
- **优化**: 使用 .dockerignore 减少构建上下文

## 常用 Docker 命令

```bash
# 查看运行的容器
docker ps

# 查看应用日志
docker logs spring-batch-app

# 进入容器
docker exec -it spring-batch-app /bin/bash

# 停止容器
docker stop spring-batch-app

# 删除容器
docker rm spring-batch-app

# 删除镜像
docker rmi spring-batch-demo:latest
```

## 环境变量配置

可以通过环境变量来配置应用：

```bash
docker run -p 8080:8080 \
  -e JAVA_OPTS="-Xmx1g -Xms512m" \
  -e SPRING_PROFILES_ACTIVE=production \
  spring-batch-demo:latest
```

## 健康检查

应用内置了健康检查端点：

- URL: http://localhost:8080/actuator/health
- 检查间隔: 30 秒
- 超时时间: 3 秒
- 重试次数: 3 次

add change
