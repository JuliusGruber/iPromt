# iPromt - MCP Server Example

This repository contains a minimal command line application that provides access to custom prompts.
Prompts are loaded from `src/main/resources/prompts.properties` at startup.

## Building

```
mvn -f mcp-server/pom.xml package
```

## Running

```
java -jar mcp-server/target/mcp-server.jar
```

When the server is running, type `list` to display available prompt names or enter a prompt name to get its text. The server reads from standard input until EOF.


