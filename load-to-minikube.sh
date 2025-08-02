#!/bin/bash

# 将已有的 Docker 镜像加载到 Minikube 的脚本

echo "=== 加载现有镜像到 Minikube ==="

# 检查 minikube 是否运行
if ! minikube status | grep -q "Running"; then
    echo "错误: Minikube 未运行，请先启动 Minikube"
    echo "启动命令: minikube start"
    exit 1
fi

# 检查本地镜像是否存在
if ! docker image inspect spring-batch-demo:latest &> /dev/null; then
    echo "错误: 本地镜像 spring-batch-demo:latest 不存在"
    echo "请先运行 ./build-docker.sh 构建镜像"
    exit 1
fi

echo "方法选择："
echo "1. 直接在 Minikube 内重新构建（推荐）"
echo "2. 保存并加载镜像文件"
echo "3. 使用 minikube image load（如果支持）"

read -p "请选择方法 (1/2/3): " method

case $method in
    1)
        echo "1. 设置 Docker 环境指向 Minikube..."
        eval $(minikube docker-env)
        
        echo "2. 在 Minikube 内重新构建镜像..."
        docker build -t spring-batch-demo:latest .
        ;;
    2)
        echo "1. 保存镜像到文件..."
        docker save spring-batch-demo:latest -o spring-batch-demo.tar
        
        echo "2. 加载镜像到 Minikube..."
        eval $(minikube docker-env)
        docker load -i spring-batch-demo.tar
        
        echo "3. 清理临时文件..."
        rm spring-batch-demo.tar
        ;;
    3)
        echo "1. 使用 minikube image load..."
        minikube image load spring-batch-demo:latest
        ;;
    *)
        echo "无效选择"
        exit 1
        ;;
esac

if [ $? -eq 0 ]; then
    echo "✅ 镜像加载成功！"
    
    echo "验证镜像..."
    eval $(minikube docker-env)
    docker images | grep spring-batch-demo
    
    echo ""
    echo "🎉 镜像已在 Minikube 中可用！"
else
    echo "❌ 镜像加载失败！"
    exit 1
fi
