FROM eclipse-temurin:21-jre
WORKDIR /app
COPY mcp-server/target/mcp-server.jar ./mcp-server.jar
ENTRYPOINT ["java", "-jar", "mcp-server.jar"]
