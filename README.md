# iPromt - MCP Server Example

This repository contains a minimal implementation of a Message Channel Protocol (MCP) server that exposes two simple tools backed by a prompt library.
Prompts are loaded from `mcp-server/src/main/resources/prompts.properties` at startup.

The server understands the standard MCP methods (`initialize`, `getManifest`, `invokeTool`, `getToolMetadata`, `getToolSchema`, and `shutdown`) and communicates via JSON-RPC over standard input and output.

## Building

```bash
mvn -f mcp-server/pom.xml package
```

## Running

```bash
java -jar mcp-server/target/mcp-server.jar
```

## Running with Docker

After building the JAR, you can package it into a Docker image:

```bash
docker build -t ipromt .
```

Run the container and interact with it using MCP JSON-RPC messages:

```bash
docker run --rm -i ipromt
```

Example request to list available prompts using MCP `invokeTool`:

```json
{"jsonrpc":"2.0","id":1,"method":"invokeTool","params":{"name":"listPrompts","input":{}}}
```

Example request to get a specific prompt:

```json
{"jsonrpc":"2.0","id":2,"method":"invokeTool","params":{"name":"getPrompt","input":{"name":"hello"}}}
```

The server will respond with a JSON-RPC response for each request.

## GitHub Copilot configuration

To use this MCP server with GitHub Copilot, add an entry to your `mcp.json` file. The `command` array should launch the server and Copilot will communicate with it over standard input:

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

Place the JAR built from this repository in the location referenced by `command`. Copilot will start the process and send MCP requests via stdin.

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
