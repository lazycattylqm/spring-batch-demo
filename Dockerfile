# 使用 Eclipse Temurin OpenJDK 17 作为基础镜像（更稳定的官方维护版本）
FROM eclipse-temurin:17-jre-jammy

# 设置工作目录
WORKDIR /app

# 设置维护者信息
LABEL maintainer="your-email@example.com"

# 安装 curl（用于健康检查）
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# 复制构建好的 JAR 文件到容器中
COPY target/spring.batch.demo-0.0.1-SNAPSHOT.jar app.jar

# 创建一个非 root 用户来运行应用程序（安全最佳实践）
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# 暴露端口（Spring Boot 默认端口 8080）
EXPOSE 8080

# 设置 JVM 参数和启动命令
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]

# 健康检查（可选）
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
