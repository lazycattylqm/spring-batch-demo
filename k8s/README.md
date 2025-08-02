# Spring Batch Demo - Kubernetes 部署指南

## 📁 文件说明

- `namespace.yaml` - 创建 `liqiming-poc` 命名空间
- `job.yaml` - 一次性执行的 Kubernetes Job
- `cronjob.yaml` - 定时执行的 Kubernetes CronJob
- `configmap.yaml` - 应用配置文件
- `deploy.sh` - 自动化部署脚本

## 🚀 快速部署

### 方法一：使用部署脚本（推荐）

```bash
# 执行部署脚本
./k8s/deploy.sh
```

脚本会提供以下选项：

- a) 仅部署一次性 Job
- b) 仅部署 CronJob
- c) 部署所有资源
- d) 清理所有资源

### 方法二：手动部署

```bash
# 1. 创建命名空间
kubectl apply -f k8s/namespace.yaml

# 2. 创建配置文件
kubectl apply -f k8s/configmap.yaml

# 3. 部署一次性 Job
kubectl apply -f k8s/job.yaml

# 4. 部署 CronJob
kubectl apply -f k8s/cronjob.yaml
```

## 📋 资源配置详情

### Namespace

- **名称**: `liqiming-poc`
- **用途**: 隔离 Spring Batch Demo 相关资源

### Job (一次性任务)

- **镜像**: `spring-batch-demo:latest`
- **资源限制**: 1Gi 内存, 500m CPU
- **资源请求**: 512Mi 内存, 250m CPU
- **重试次数**: 3
- **完成后保留时间**: 1 小时

### CronJob (定时任务)

- **调度**: `*/3 * * * *` (每 3 分钟执行一次)
- **时区**: `Asia/Shanghai`
- **并发策略**: `Forbid` (禁止并发执行)
- **成功历史保留**: 2 个
- **失败历史保留**: 2 个

### ConfigMap

- **应用配置**: 分别为一次性任务和定时任务提供不同的配置
- **日志配置**: 包含详细的日志格式和级别设置
- **健康检查**: 启用 Spring Boot Actuator 端点

## 🔧 常用操作命令

### 查看资源状态

```bash
# 查看所有资源
kubectl get all -n liqiming-poc

# 查看 Pods
kubectl get pods -n liqiming-poc

# 查看 Jobs
kubectl get jobs -n liqiming-poc

# 查看 CronJobs
kubectl get cronjobs -n liqiming-poc

# 查看 ConfigMap
kubectl get configmap -n liqiming-poc
```

### 查看日志

```bash
# 查看 Pod 日志
kubectl logs <pod-name> -n liqiming-poc

# 实时查看日志
kubectl logs -f <pod-name> -n liqiming-poc

# 查看 Job 的所有 Pod 日志
kubectl logs -l app=spring-batch-demo -n liqiming-poc
```

### 调试和排错

```bash
# 查看资源详细信息
kubectl describe job spring-batch-job -n liqiming-poc
kubectl describe cronjob spring-batch-cronjob -n liqiming-poc

# 查看 Pod 详细信息
kubectl describe pod <pod-name> -n liqiming-poc

# 进入容器
kubectl exec -it <pod-name> -n liqiming-poc -- /bin/bash
```

### 手动触发 CronJob

```bash
# 基于 CronJob 创建一个立即执行的 Job
kubectl create job --from=cronjob/spring-batch-cronjob manual-job-$(date +%s) -n liqiming-poc
```

### 清理资源

```bash
# 删除特定资源
kubectl delete job spring-batch-job -n liqiming-poc
kubectl delete cronjob spring-batch-cronjob -n liqiming-poc

# 删除整个命名空间（清理所有资源）
kubectl delete namespace liqiming-poc
```

## ⚙️ 自定义配置

### 修改定时任务执行时间

编辑 `cronjob.yaml` 中的 `schedule` 字段：

```yaml
# 每3分钟执行一次（当前配置）
schedule: "*/3 * * * *"

# 每小时执行一次
schedule: "0 * * * *"

# 每天上午9点执行
schedule: "0 9 * * *"

# 每周一上午9点执行
schedule: "0 9 * * 1"

# 每5分钟执行一次
schedule: "*/5 * * * *"
```

### 修改资源配置

编辑 YAML 文件中的 `resources` 部分：

```yaml
resources:
  limits:
    memory: '2Gi' # 增加内存限制
    cpu: '1' # 增加 CPU 限制
  requests:
    memory: '1Gi' # 增加内存请求
    cpu: '500m' # 增加 CPU 请求
```

### 添加环境变量

在容器的 `env` 部分添加：

```yaml
env:
  - name: CUSTOM_ENV_VAR
    value: 'custom-value'
  - name: DB_PASSWORD
    valueFrom:
      secretKeyRef:
        name: db-secret
        key: password
```

## 🔍 监控和日志

### 健康检查

应用暴露了 Spring Boot Actuator 端点：

- `/actuator/health` - 健康检查
- `/actuator/info` - 应用信息
- `/actuator/metrics` - 指标信息

### 日志聚合

建议配置日志聚合工具（如 ELK Stack）来集中管理日志：

```yaml
# 在容器中添加日志卷挂载
volumeMounts:
  - name: logs-volume
    mountPath: /app/logs

volumes:
  - name: logs-volume
    emptyDir: {}
```

## 🚨 注意事项

1. **镜像策略**: 当前设置为 `imagePullPolicy: Never`，适用于本地镜像。如果使用远程镜像仓库，请改为 `Always` 或 `IfNotPresent`。

2. **资源限制**: 根据实际应用需求调整内存和 CPU 限制。

3. **持久化存储**: 如果需要持久化数据，请配置 PersistentVolume 和 PersistentVolumeClaim。

4. **安全性**: 在生产环境中，建议配置 RBAC、NetworkPolicy 和 Pod Security Policy。

5. **定时任务重叠**: CronJob 设置为 `Forbid` 并发策略，确保前一个任务完成后才执行下一个。
