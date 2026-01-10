# 1. Build Stage
FROM gradle:jdk21 AS build
WORKDIR /app

# Gradle 캐싱을 위해 의존성 파일 먼저 복사
COPY build.gradle settings.gradle ./
COPY src src

# 전체 소스 복사 (필요 시 .dockerignore 설정 권장)
COPY . .

# Gradle Wrapper 실행 권한 부여 및 빌드 (테스트 제외)
RUN chmod +x gradlew
RUN ./gradlew clean build -x test --no-daemon

# 2. Run Stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# 빌드 결과물(jar) 복사
COPY --from=build /app/build/libs/*.jar app.jar

# Render 등 클라우드 환경 포트 설정
ENV PORT=8080
EXPOSE 8080

# Render 등 클라우드 환경의 PORT 변수 대응
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
