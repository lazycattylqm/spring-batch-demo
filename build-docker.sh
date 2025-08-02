#!/bin/bash

# 构建 Docker 镜像的脚本

# 设置镜像名称和标签
IMAGE_NAME="spring-batch-demo"
IMAGE_TAG="latest"

# 支持使用不同的 Dockerfile
DOCKERFILE="Dockerfile"
if [ "$1" = "corretto" ]; then
    DOCKERFILE="Dockerfile.corretto"
    IMAGE_TAG="corretto"
elif [ "$1" = "microsoft" ]; then
    DOCKERFILE="Dockerfile.microsoft"
    IMAGE_TAG="microsoft"
fi

echo "开始构建 Docker 镜像..."
echo "使用 Dockerfile: $DOCKERFILE"
echo "镜像标签: ${IMAGE_NAME}:${IMAGE_TAG}"

# 确保 JAR 文件存在
if [ ! -f "target/spring.batch.demo-0.0.1-SNAPSHOT.jar" ]; then
    echo "错误: 找不到 JAR 文件。请先运行 'mvn clean package' 构建项目。"
    exit 1
fi

# 构建 Docker 镜像
docker build -f $DOCKERFILE -t ${IMAGE_NAME}:${IMAGE_TAG} .

if [ $? -eq 0 ]; then
    echo "Docker 镜像构建成功！"
    echo "镜像名称: ${IMAGE_NAME}:${IMAGE_TAG}"
    echo ""
    echo "运行容器的命令:"
    echo "docker run -p 8080:8080 ${IMAGE_NAME}:${IMAGE_TAG}"
    echo ""
    echo "后台运行容器的命令:"
    echo "docker run -d -p 8080:8080 --name spring-batch-app ${IMAGE_NAME}:${IMAGE_TAG}"
else
    echo "Docker 镜像构建失败！"
    exit 1
fi
