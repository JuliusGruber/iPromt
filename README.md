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

## GitHub Copilot configuration

To use this MCP server with GitHub Copilot, add an entry to your `mcp.json` file. The `command` array should launch the JAR and Copilot will communicate with it over standard input:

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


