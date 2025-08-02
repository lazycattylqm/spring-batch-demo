#!/bin/bash

# Kubernetes 部署脚本

echo "=== Spring Batch Demo Kubernetes 部署脚本 ==="

# 检查 kubectl 是否可用
if ! command -v kubectl &> /dev/null; then
    echo "错误: kubectl 未安装或不在 PATH 中"
    exit 1
fi

# 检查 Docker 镜像是否存在
if ! docker image inspect spring-batch-demo:latest &> /dev/null; then
    echo "错误: Docker 镜像 spring-batch-demo:latest 不存在"
    echo "请先运行 ./build-docker.sh 构建镜像"
    exit 1
fi

echo "1. 创建 namespace..."
kubectl apply -f k8s/namespace.yaml

echo "2. 创建 ConfigMap..."
kubectl apply -f k8s/configmap.yaml

echo "3. 部署资源选择："
echo "   a) 仅部署一次性 Job"
echo "   b) 仅部署 CronJob"
echo "   c) 部署所有资源"
echo "   d) 清理所有资源"
echo

read -p "请选择 (a/b/c/d): " choice

case $choice in
    a|A)
        echo "部署一次性 Job..."
        kubectl apply -f k8s/job.yaml
        echo "查看 Job 状态："
        kubectl get jobs -n liqiming-poc
        ;;
    b|B)
        echo "部署 CronJob..."
        kubectl apply -f k8s/cronjob.yaml
        echo "查看 CronJob 状态："
        kubectl get cronjobs -n liqiming-poc
        ;;
    c|C)
        echo "部署所有资源..."
        kubectl apply -f k8s/job.yaml
        kubectl apply -f k8s/cronjob.yaml
        echo "查看部署状态："
        kubectl get all -n liqiming-poc
        ;;
    d|D)
        echo "清理所有资源..."
        kubectl delete namespace liqiming-poc
        echo "资源已清理完成"
        ;;
    *)
        echo "无效选择"
        exit 1
        ;;
esac

if [[ $choice != "d" && $choice != "D" ]]; then
    echo ""
    echo "=== 常用命令 ==="
    echo "查看 Pods:           kubectl get pods -n liqiming-poc"
    echo "查看 Jobs:           kubectl get jobs -n liqiming-poc"
    echo "查看 CronJobs:       kubectl get cronjobs -n liqiming-poc"
    echo "查看 Pod 日志:       kubectl logs <pod-name> -n liqiming-poc"
    echo "查看 ConfigMap:      kubectl get configmap -n liqiming-poc"
    echo "描述资源:           kubectl describe <resource-type> <resource-name> -n liqiming-poc"
    echo "删除 namespace:      kubectl delete namespace liqiming-poc"
fi
