package org.example.mcp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.PromptService;

public class GetPromptTool implements Tool {
    private final PromptService service;
    private final ObjectMapper mapper = new ObjectMapper();

    public GetPromptTool(PromptService service) {
        this.service = service;
    }

    @Override
    public String getName() {
        return "getPrompt";
    }

    @Override
    public String getDescription() {
        return "Retrieve a prompt by name";
    }

    @Override
    public JsonNode getInputSchema() {
        ObjectNode schema = mapper.createObjectNode();
        schema.put("type", "object");
        ObjectNode props = schema.putObject("properties");
        props.putObject("name").put("type", "string");
        schema.putArray("required").add("name");
        return schema;
    }

    @Override
    public JsonNode getOutputSchema() {
        ObjectNode schema = mapper.createObjectNode();
        schema.put("type", "object");
        ObjectNode props = schema.putObject("properties");
        props.putObject("prompt").put("type", "string");
        schema.putArray("required").add("prompt");
        return schema;
    }

    @Override
    public JsonNode invoke(JsonNode input) {
        String name = input.path("name").asText(null);
        ObjectNode result = mapper.createObjectNode();
        String prompt = service.getPrompt(name);
        if (prompt != null) {
            result.put("prompt", prompt);
        } else {
            result.putNull("prompt");
        }
        return result;
    }
}
