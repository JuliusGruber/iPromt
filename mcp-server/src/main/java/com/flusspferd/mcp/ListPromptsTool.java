package com.flusspferd.mcp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.flusspferd.PromptService;

public class ListPromptsTool implements Tool {
    private final PromptService service;
    private final ObjectMapper mapper = new ObjectMapper();

    public ListPromptsTool(PromptService service) {
        this.service = service;
    }

    @Override
    public String getName() {
        return "listPrompts";
    }

    @Override
    public String getDescription() {
        return "List available prompt names";
    }

    @Override
    public JsonNode getInputSchema() {
        ObjectNode schema = mapper.createObjectNode();
        schema.put("type", "object");
        schema.putObject("properties");
        schema.putArray("required");
        return schema;
    }

    @Override
    public JsonNode getOutputSchema() {
        ObjectNode schema = mapper.createObjectNode();
        schema.put("type", "object");
        ObjectNode props = schema.putObject("properties");
        ObjectNode names = props.putObject("names");
        names.put("type", "array");
        names.putObject("items").put("type", "string");
        schema.putArray("required").add("names");
        return schema;
    }

    @Override
    public JsonNode invoke(JsonNode input) {
        ArrayNode arr = mapper.createArrayNode();
        for (String name : service.names()) {
            arr.add(name);
        }
        ObjectNode result = mapper.createObjectNode();
        result.set("names", arr);
        return result;
    }
}
