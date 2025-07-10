package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.mcp.GetPromptTool;
import org.example.mcp.ListPromptsTool;
import org.example.mcp.Tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    private final Map<String, Tool> tools = new LinkedHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private boolean running = true;

    public Main() {
        PromptService service = new PromptService();
        register(new ListPromptsTool(service));
        register(new GetPromptTool(service));
    }

    private void register(Tool tool) {
        tools.put(tool.getName(), tool);
    }

    private JsonNode manifest() {
        ObjectNode m = mapper.createObjectNode();
        m.put("name", "ipromt");
        m.put("description", "Example MCP server for prompts");
        m.put("version", "1.0");
        m.put("schemaVersion", "v1");
        var arr = m.putArray("tools");
        for (Tool t : tools.values()) {
            ObjectNode tool = arr.addObject();
            tool.put("name", t.getName());
            tool.put("description", t.getDescription());
        }
        return m;
    }

    private JsonNode toolMetadata(String name) {
        Tool t = tools.get(name);
        if (t == null) return null;
        ObjectNode meta = mapper.createObjectNode();
        meta.put("name", t.getName());
        meta.put("description", t.getDescription());
        return meta;
    }

    private JsonNode toolSchema(String name) {
        Tool t = tools.get(name);
        if (t == null) return null;
        ObjectNode schema = mapper.createObjectNode();
        schema.set("input", t.getInputSchema());
        schema.set("output", t.getOutputSchema());
        return schema;
    }

    private JsonNode invoke(String name, JsonNode input) throws Exception {
        Tool t = tools.get(name);
        if (t == null) return null;
        return t.invoke(input);
    }

    private void run() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while (running && (line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            JsonNode req;
            try {
                req = mapper.readTree(line);
            } catch (Exception e) {
                continue; // ignore malformed
            }
            String method = req.path("method").asText();
            JsonNode params = req.path("params");
            JsonNode idNode = req.get("id");
            ObjectNode resp = mapper.createObjectNode();
            resp.put("jsonrpc", "2.0");
            if (idNode != null) resp.set("id", idNode);
            try {
                switch (method) {
                    case "initialize" -> resp.set("result", mapper.createObjectNode().put("ready", true));
                    case "getManifest" -> resp.set("result", manifest());
                    case "getToolMetadata" -> {
                        String name = params.path("name").asText(null);
                        JsonNode meta = toolMetadata(name);
                        if (meta == null) throw new IllegalArgumentException("Unknown tool");
                        resp.set("result", meta);
                    }
                    case "getToolSchema" -> {
                        String name = params.path("name").asText(null);
                        JsonNode schema = toolSchema(name);
                        if (schema == null) throw new IllegalArgumentException("Unknown tool");
                        resp.set("result", schema);
                    }
                    case "invokeTool" -> {
                        String name = params.path("name").asText(null);
                        JsonNode input = params.path("input");
                        JsonNode out = invoke(name, input);
                        if (out == null) throw new IllegalArgumentException("Unknown tool");
                        resp.set("result", out);
                    }
                    case "shutdown" -> {
                        resp.set("result", mapper.createObjectNode());
                        running = false;
                    }
                    default -> throw new IllegalArgumentException("Method not found");
                }
            } catch (Exception e) {
                ObjectNode err = mapper.createObjectNode();
                err.put("code", -32602);
                err.put("message", e.getMessage());
                resp.set("error", err);
            }
            System.out.println(mapper.writeValueAsString(resp));
        }
    }

    public static void main(String[] args) throws Exception {
        new Main().run();
    }
}
