# 1단계: Gradle로 빌드 (빌더 스테이지)
FROM gradle:8.6-jdk17 AS builder

WORKDIR /app

# 의존성 캐시 최적화를 위해 먼저 gradle 폴더와 주요 빌드파일 복사
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 의존성 미리 받아오기 (optional)
RUN gradle dependencies --no-daemon || true

# 전체 프로젝트 복사
COPY . .

# Spring Boot JAR 빌드
RUN gradle bootJar --no-daemon

# 2단계: 실행을 위한 경량 이미지
FROM openjdk:17-jdk-alpine

# JAR 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 앱 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
