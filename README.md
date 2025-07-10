# MCP Server Example

This repository includes a minimal [Quarkus](https://quarkus.io/) application that exposes custom prompts over HTTP.

## Building

```
mvn -f mcp-server/pom.xml package
```

## Running

```
java -jar mcp-server/target/quarkus-app/quarkus-run.jar
```

The service exposes two endpoints:

- `GET /iprompt` – list available prompt names.
- `GET /iprompt/{name}` – fetch a specific prompt value.

You can extend `PromptResource` with your own prompt definitions.
