# 使用OpenJDK 8作为基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 安装Maven
RUN apk add --no-cache maven

# 复制pom.xml和源代码
COPY pom.xml .
COPY src ./src

# 编译项目
RUN mvn clean package -DskipTests

# 暴露端口
EXPOSE 8080

# 运行应用
ENTRYPOINT ["java", "-jar", "target/springboot-0.0.1-SNAPSHOT.jar"]

