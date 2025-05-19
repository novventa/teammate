# 1단계: Build Stage
FROM gradle:8.2.0-jdk17 AS build
WORKDIR /app

# 종속성 캐싱 최적화를 위해 설정 파일만 먼저 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || return 0

# 전체 프로젝트 복사 후 빌드
COPY . .
RUN gradle clean bootJar --no-daemon

# 2단계: Runtime Stageq
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# 빌드 결과 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]