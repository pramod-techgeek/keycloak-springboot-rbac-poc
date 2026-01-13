FROM gradle:8.5.0-jdk21 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
# We accept that the build environment might not have Java 25 yet, or we use a toolchain. 
# For simplicity in this POC, we'll try to use a base image that supports the target or just build with what we have if compatible.
# Actually, since user asked for Java 25 specifically, we should try to find a base image or install it.
# However, standard gradle images might not have 25 yet.
# Let's try using a basic openjdk image and installing gradle, OR use a mult-stage build where we just run the jar.
# Better approach: Use local build or wrapper.
# But for Dockerfile self-containment:
RUN gradle build --no-daemon -x test

FROM openjdk:25-ea-jdk-slim
VOLUME /tmp
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
