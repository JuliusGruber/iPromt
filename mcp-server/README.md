# iPrompt MCP Server

This project provides an MCP prompt server powered by Quarkus.

## Prerequisites

- Java 17 or higher
- Maven 3.8+ (or compatible)

## Building

Run Maven to build the project:

```
mvn clean package
```

## Running the Server

Start the MCP server over stdio transport:

```
java -jar target/quarkus-app/quarkus-run.jar mcp:stdio
```

## Start Inspector and retrieve MCP prompt

```
npx @modelcontextprotocol/inspector --config src/main/resources/inspector-mcp-config.json --server quarkus --cli --method prompts/list
```
```
npx @modelcontextprotocol/inspector --config src/main/resources/inspector-mcp-config.json --server quarkus --cli --method prompts/get --prompt-name meanGreeting
```

