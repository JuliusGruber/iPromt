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

## Running with Docker

After building the JAR, you can package it into a Docker image:

```
docker build -t ipromt .
```

Run the container and interact with it over standard input. Each line you send must be a JSON-RPC request:

```
docker run --rm -i ipromt
```

Example request to list available prompts:

```json
{"jsonrpc":"2.0","id":1,"method":"listPrompts"}
```

Example request to get a specific prompt:

```json
{"jsonrpc":"2.0","id":2,"method":"getPrompt","params":{"name":"hello"}}
```

The server will respond with a JSON-RPC response per request.


## GitHub Copilot configuration

To use this MCP server with GitHub Copilot, add an entry to your `mcp.json` file. The `command` array should launch the server and Copilot will communicate with it over standard input. When running the JAR directly it looks like this:

```json
{
  "servers": [
    {
      "id": "ipromt",
      "command": ["java", "-jar", "/path/to/mcp-server.jar"],
      "stdin": true
    }
  ]
}
```

Place the JAR built from this repository in the location referenced by `command`. Copilot will start the process and send prompt requests via stdin.

If you package the server into Docker instead, your `command` array should start the container:

```json
{
  "servers": [
    {
      "id": "ipromt",
      "command": ["docker", "run", "--rm", "-i", "ipromt"],
      "stdin": true
    }
  ]
}
```


