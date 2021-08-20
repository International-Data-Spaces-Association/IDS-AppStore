# Dependencies
FROM maven:3-jdk-11 AS maven
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve

# Plugins
RUN mvn -e -B dependency:resolve-plugins

# Classes
COPY src/main/java ./src/main/java
COPY src/main/resources ./src/main/resources
RUN mvn -e -B clean package -DskipTests -Dmaven.javadoc.skip=true

# Copy the jar and build image
FROM exoplatform/jdk:openjdk-11-ubuntu-2004
COPY --from=maven /app/target/*.jar /app/app.jar
WORKDIR /app
RUN mkdir -p /data/search
EXPOSE 8080
EXPOSE 29292
# USER nonroot # Run root in debug images
ENTRYPOINT ["java","-jar","app.jar"]
