ARG CS_API_KEY="CS_API_KEY"
ARG CS_API_SECRET="CS_API_SECRET"
ARG CS_FROM_NUMBER="CS_FROM_NUMBER"
ARG SECRET="SECRET"

# 빌드 스테이지
FROM gradle:7.5.1-jdk8 as build
ARG CS_API_KEY
ARG CS_API_SECRET
ARG CS_FROM_NUMBER
ARG SECRET

# spring 그룹과 유저을 생성하고 해당 그룹:유저로 빌드를 함으로써 리스크를 최소화함
RUN adduser --system --group spring
RUN mkdir /workspace && mkdir /workspace/app
RUN chown spring:spring /workspace/app

USER spring:spring

WORKDIR /workspace/app

COPY --chown=spring:spring gradle gradle

# Gradle 설정, 빌드 파일 및 바이너리 파일을 빌드환경에 복사함
COPY --chown=spring:spring build.gradle.kts settings.gradle.kts gradlew ./

# 소스 코드 복사
COPY --chown=spring:spring src src

ENV COOL_SMS_API_KEY $CS_API_KEY
ENV COOL_SMS_API_SECRET $CS_API_SECRET
ENV COOL_SMS_FROM_NUMBER $CS_FROM_NUMBER
ENV USER_AUTH_SECRET $SECRET

# 빌드 실행
RUN ./gradlew build -x test
RUN mkdir -p build/libs/dependency && (cd build/libs/dependency; jar -xf ../*SNAPSHOT.jar)


ENV COOL_SMS_API_KEY ${CS_API_KEY}
ENV COOL_SMS_API_SECRET ${CS_API_SECRET}
ENV COOL_SMS_FROM_NUMBER ${CS_FROM_NUMBER}
ENV USER_AUTH_SECRET ${SECRET}

# 실제
FROM openjdk:8-jre-slim-buster
ARG CS_API_KEY
ARG CS_API_SECRET
ARG CS_FROM_NUMBER
ARG SECRET

# spring 유저와 그룹을 생성하고 해당 그룹:유저로 실행을 함으로써 리스크를 최소화함
RUN adduser --system --group spring
USER spring:spring

VOLUME /tmp


ENV COOL_SMS_API_KEY ${COOL_SMS_API_KEY}
ENV COOL_SMS_API_SECRET ${COOL_SMS_API_SECRET}
ENV COOL_SMS_FROM_NUMBER ${COOL_SMS_FROM_NUMBER}
ENV USER_AUTH_SECRET ${USER_AUTH_SECRET}


ARG DEPENDENCY=/workspace/app/build/libs/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.samcho.user_authentication.UserAuthenticationApplicationKt"]

# only expose port 8080
EXPOSE 8080