package org.example.mcp;

import com.fasterxml.jackson.databind.JsonNode;

public interface Tool {
    String getName();
    String getDescription();
    JsonNode getInputSchema();
    JsonNode getOutputSchema();
    JsonNode invoke(JsonNode input) throws Exception;
}
